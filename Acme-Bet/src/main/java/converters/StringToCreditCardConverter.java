package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CreditCardRepository;

import domain.CreditCard;



@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String,CreditCard> {

	@Autowired
	CreditCardRepository repository;
	
	@Override
	public CreditCard convert(String s) {
		CreditCard res;
		int id;
		
		try {
			if(StringUtils.isEmpty(s))
				res=null;
			else{
				id = Integer.valueOf(s);
				res = repository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return res;
	}

}
