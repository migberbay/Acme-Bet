package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BetPoolRepository;

import domain.BetPool;



@Component
@Transactional
public class StringToBetPoolConverter implements Converter<String,BetPool> {

	@Autowired
	BetPoolRepository repository;
	
	@Override
	public BetPool convert(String s) {
		BetPool res;
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
