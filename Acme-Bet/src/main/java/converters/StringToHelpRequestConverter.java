package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HelpRequestRepository;

import domain.HelpRequest;



@Component
@Transactional
public class StringToHelpRequestConverter implements Converter<String,HelpRequest> {

	@Autowired
	HelpRequestRepository repository;
	
	@Override
	public HelpRequest convert(String s) {
		HelpRequest res;
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
