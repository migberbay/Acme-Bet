package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Counselor;


@Component
@Transactional
public class CounselorToStringConverter implements Converter<Counselor,String> {

	@Override
	public String convert(Counselor o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
