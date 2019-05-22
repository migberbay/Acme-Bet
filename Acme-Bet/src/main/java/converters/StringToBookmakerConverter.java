package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.BookmakerRepository;

import domain.Bookmaker;



@Component
@Transactional
public class StringToBookmakerConverter implements Converter<String,Bookmaker> {

	@Autowired
	BookmakerRepository repository;
	
	@Override
	public Bookmaker convert(String s) {
		Bookmaker res;
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
