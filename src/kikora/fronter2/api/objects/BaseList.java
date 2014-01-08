package kikora.fronter2.api.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public abstract class BaseList<K>
{
	public static class ListLinks
	{
		private Link self;
		private Link find;
		private Link next;
		
		public Link getSelf()
		{
			return self;
		}
		
		public Link getFind()
		{
			return find;
		}
		
		public Link getNext()
		{
			return next;
		}

		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append("ListLinks [self=");
			builder.append(self);
			builder.append(", find=");
			builder.append(find);
			builder.append(", next=");
			builder.append(next);
			builder.append("]");
			
			return builder.toString();
		}
	}
	
	private int total;
	private ListLinks _links;
	private Map<String,List<K>> _embedded;
	
	public int getTotal()
	{
		return total;
	}
	
	public Link getSelfLink()
	{
		return _links.getSelf();
	}

	public Link getFindLink()
	{
		return _links.getFind();
	}

	public Link getNextLink()
	{
		return _links.getNext();
	}
	
	protected List<K> getList(String name)
	{
		List<K> list = _embedded.get(name);
			
		if(list == null)
		{
			list = new ArrayList<>();
		}

		return list;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("BaseList [total=");
		builder.append(total);
		builder.append(", _links=");
		builder.append(_links);
		builder.append(", _embedded=");
		builder.append(_embedded);
		builder.append("]");
		
		return builder.toString();
	}
}
