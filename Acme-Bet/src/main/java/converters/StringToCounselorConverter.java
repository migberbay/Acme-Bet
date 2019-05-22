package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CounselorRepository;

import domain.Counselor;



@Component
@Transactional
public class StringToCounselorConverter implements Converter<String,Counselor> {

	@Autowired
	CounselorRepository repository;
	
	@Override
	public Counselor convert(String s) {
		Counselor res;
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
