
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Box;
import domain.Message;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed Repository -----
	@Autowired
	private MessageRepository	messageRepository;

	// Supporting Services -----

	@Autowired
	private BoxService			boxService;

	@Autowired
	private UserAccountService	userAccountService;


	// Simple CRUD methods -----
	public Message create(final UserAccount sender) {

		final Message message = new Message();

		message.setSender(sender);
		message.setPriority("NEUTRAL");
		message.setFlagSpam(false);
		message.setTags(new ArrayList<String>());
		message.setMoment(new Date(System.currentTimeMillis() - 1000));

		return message;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int Id) {
		return this.messageRepository.findOne(Id);
	}

	public Message save(final Message message) {

		Message result;

		final Date moment = new Date(System.currentTimeMillis() - 1000);

		message.setMoment(moment);

		result = this.messageRepository.saveAndFlush(message);
		return result;
	}

	public Message reconstruct(final MessageForm form) {
		final Message res = this.create(LoginService.getPrincipal());
		res.setBody(form.getBody());
		res.setPriority(form.getPriority());
		res.setSubject(form.getSubject());

		final List<UserAccount> recipients = new ArrayList<>();
		final List<String> tags = new ArrayList<>();

		final String[] recipientsArray = form.getRecipients().split(",");
		final String[] tagsArray = form.getTags().split(",");

		for (int i = 0; i < tagsArray.length; i++)
			tags.add(tagsArray[i].trim());

		for (int i = 0; i < recipientsArray.length; i++)
			if (!(recipientsArray[i].length() < 5))
				recipients.add(this.userAccountService.findByUsername(recipientsArray[i].trim()));

		res.setTags(tags);
		res.setRecipients(recipients);

		//TODO: missing validator until bugfix is found.

		return res;
	}

	public void delete(final Message message) {

		// El mensaje se movera a la trashbox, si el mensaje ya estaba en la
		// trashbox se elimina del sistema.

		final UserAccount userAccount = LoginService.getPrincipal();

		//añadimos todas las boxes de los actores que tienen el message a allActorBoxes
		final Set<Box> allActorBoxes = new HashSet<>();

		final Collection<UserAccount> recipients = message.getRecipients();
		for (final UserAccount ua : recipients)
			allActorBoxes.addAll(this.boxService.findByUserAccountId(ua.getId()));
		final UserAccount sender = message.getSender();
		allActorBoxes.addAll(this.boxService.findByUserAccountId(sender.getId()));

		//Vemos que actor de los que tiene el message es el que esta logeado.
		UserAccount logged = null;

		for (final UserAccount recipient : recipients)
			if (recipient.equals(userAccount)) {
				logged = recipient;
				break;
			}

		if (sender.equals(userAccount))
			logged = sender;

		//localizamos la trashbox y separamos los otros boxes en otherboxes para el actor logeado.
		Box trash = null;
		final Collection<Box> otherboxes = new ArrayList<Box>();

		for (final Box box : this.boxService.findByUserAccountId(logged.getId()))
			if (box.getName().equals("Trash Box"))
				trash = box;
			else
				otherboxes.add(box);

		//comprobar si esta en trashbox
		if (trash.getMessages().contains(message.getId())) {
			final Collection<Integer> aux = trash.getMessages();
			aux.remove(message.getId());
			trash.setMessages(aux);
			allActorBoxes.remove(trash);

			//comprobamos si el mensaje esta en alguna otra box.
			boolean isInOtherBox = false;
			for (final Box b : allActorBoxes)
				if (b.getMessages().contains(message.getId())) {
					isInOtherBox = true;
					break;
				}
			if (!isInOtherBox)
				this.messageRepository.delete(message);

			this.boxService.save(trash);
		} else
			for (final Box b : otherboxes)
				if (b.getMessages().contains(message.getId())) {
					final Collection<Integer> aux = b.getMessages();
					aux.remove(message.getId());
					b.setMessages(aux);

					final Collection<Integer> t = trash.getMessages();
					t.add(message.getId());
					trash.setMessages(t);

					this.boxService.save(trash);
					this.boxService.save(b);
				}
	}

	// Other business methods -----

	public void addMesageToBoxes(final Message message) {
		final Collection<Box> boxes = new ArrayList<Box>();
		final Box outBox = this.boxService.findByUserAccountAndName(message.getSender(), "Out Box");

		final Authority adminAuth = new Authority();
		adminAuth.setAuthority(Authority.ADMIN);

		boxes.add(outBox);
		for (final UserAccount ua : message.getRecipients()) {
			final Box spamBox = this.boxService.findByUserAccountAndName(ua, "Spam Box");
			boxes.add(spamBox);

		}
		if (message.getSender().getAuthorities().contains(adminAuth))
			for (final UserAccount ua : message.getRecipients()) {
				final Box notificationBox = this.boxService.findByUserAccountAndName(ua, "Notification Box");
				boxes.add(notificationBox);
			}
		else
			for (final UserAccount ua : message.getRecipients()) {
				final Box inBox = this.boxService.findByUserAccountAndName(ua, "In Box");
				boxes.add(inBox);

			}

		for (final Box box : boxes) {
			final Collection<Integer> messages = new ArrayList<>(box.getMessages());
			messages.add(message.getId());
			box.setMessages(messages);
			this.boxService.save(box);
		}
	}

	public Collection<Message> findBySender(final UserAccount sender) {
		return this.messageRepository.findBySender(sender);
	}

}
