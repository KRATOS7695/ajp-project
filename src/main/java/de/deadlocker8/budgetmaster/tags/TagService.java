package de.deadlocker8.budgetmaster.tags;

import de.deadlocker8.budgetmaster.services.Resetable;
import org.padler.natorder.NaturalOrderComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService implements Resetable
{
	private final TagRepository tagRepository;

	@Autowired
	public TagService(TagRepository tagRepository)
	{
		this.tagRepository = tagRepository;
	}

	public TagRepository getRepository()
	{
		return tagRepository;
	}

	@Override
	public void deleteAll()
	{
		tagRepository.deleteAll();
	}

	@Override
	public void createDefaults()
	{
	}

	public List<Tag> getAllTagsAsc()
	{
		final List<Tag> tags = tagRepository.findAllByOrderByNameAsc();
		tags.sort((t1, t2) -> new NaturalOrderComparator().compare(t1.getName(), t2.getName()));
		return tags;
	}
}
