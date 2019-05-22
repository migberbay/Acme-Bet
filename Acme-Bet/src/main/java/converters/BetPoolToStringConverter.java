package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BetPool;


@Component
@Transactional
public class BetPoolToStringConverter implements Converter<BetPool,String> {

	@Override
	public String convert(BetPool o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
