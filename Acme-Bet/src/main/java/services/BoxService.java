
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	@Autowired
	private BoxRepository	boxRepository;


	public Box create(final UserAccount userAccount) {

		Assert.isTrue(userAccount.equals(userAccount));

		final Box box = new Box();

		box.setUserAccount(userAccount);
		box.setSystemBox(false);
		box.setMessages(new ArrayList<Integer>());

		return box;
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final int Id) {
		return this.boxRepository.findOne(Id);
	}

	public Collection<Box> findByUserAccountId(final int userAccountId) {
		return this.boxRepository.findByUserAccountId(userAccountId);
	}

	public Box save(final Box box) {

		Box result;

		//		UserAccount principal = LoginService.getPrincipal();
		//		Assert.isTrue(box.getUserAccount().getUserAccount().equals(principal));

		result = this.boxRepository.saveAndFlush(box);
		return result;
	}

	public void delete(final Box box) {

		Assert.isTrue(box.getSystemBox().equals(false));

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(box.getUserAccount().equals(userAccount));

		this.boxRepository.delete(box);
	}

	public Box reconstructBox(final Box box) {

		Box res;
		if (box.getId() == 0) {
			res = this.create(LoginService.getPrincipal());
			res.setName(box.getName());
			res.setParentBox(box.getParentBox());

		} else {
			res = this.findOne(box.getId());
			res.setName(box.getName());
			res.setParentBox(box.getParentBox());
		}

		return res;
	}

	// Other business methods -----

	public void createSystemBoxes(final UserAccount userAccount) {

		final Box inbox = new Box(), outbox = new Box(), thrashbox = new Box(), spambox = new Box(), notification = new Box();
		inbox.setUserAccount(userAccount);
		inbox.setName("In Box");
		inbox.setSystemBox(true);
		inbox.setMessages(new ArrayList<Integer>());
		outbox.setUserAccount(userAccount);
		outbox.setName("Out Box");
		outbox.setSystemBox(true);
		outbox.setMessages(new ArrayList<Integer>());
		thrashbox.setUserAccount(userAccount);
		thrashbox.setName("Thrash Box");
		thrashbox.setSystemBox(true);
		thrashbox.setMessages(new ArrayList<Integer>());
		spambox.setUserAccount(userAccount);
		spambox.setName("Spam Box");
		spambox.setSystemBox(true);
		spambox.setMessages(new ArrayList<Integer>());
		notification.setUserAccount(userAccount);
		notification.setName("Notification Box");
		notification.setSystemBox(true);
		notification.setMessages(new ArrayList<Integer>());

		this.saveSystem(inbox);
		this.saveSystem(outbox);
		this.saveSystem(thrashbox);
		this.saveSystem(spambox);
		this.saveSystem(notification);
	}

	public void addMessageToBox(final Box box, final Message message) {
		final List<Integer> aux = new ArrayList<>(box.getMessages());

		aux.add(0, message.getId()); // los mensajes nuevos siempre se ponen primero.
		box.setMessages(aux);
		this.save(box);

	}

	public Box findByUserAccountAndName(final UserAccount userAccount, final String boxName) {
		Assert.notNull(userAccount);
		return this.boxRepository.findByUserAccountIdAndName(userAccount.getId(), boxName);
	}

	public Box saveSystem(final Box box) {
		return this.boxRepository.save(box);
	}

	public Collection<Box> findByParentBox(final Box box) {
		return this.boxRepository.findByParent(box);
	}

}
