package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.HelpRequest;


@Component
@Transactional
public class HelpRequestToStringConverter implements Converter<HelpRequest,String> {

	@Override
	public String convert(HelpRequest o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
