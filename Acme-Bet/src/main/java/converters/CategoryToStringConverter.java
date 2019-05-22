package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;


@Component
@Transactional
public class CategoryToStringConverter implements Converter<Category,String> {

	@Override
	public String convert(Category o) {
		String res;
		
		if(o == null)
			res = null;
		else
			res= String.valueOf(o.getId());
		
		return res;
	}

}
