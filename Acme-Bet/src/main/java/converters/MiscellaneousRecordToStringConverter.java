package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousRecord;


@Component
@Transactional
public class MiscellaneousRecordToStringConverter implements Converter<MiscellaneousRecord,String> {

	@Override
	public String convert(MiscellaneousRecord o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
