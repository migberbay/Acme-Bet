package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Bookmaker;


@Component
@Transactional
public class BookmakerToStringConverter implements Converter<Bookmaker,String> {

	@Override
	public String convert(Bookmaker o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
