package org.abhishek.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.abhishek.messenger.database.DatabaseClass;
import org.abhishek.messenger.exception.DataNotFoundException;
import org.abhishek.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1, "Hello World", "abhishek"));
		messages.put(2L, new Message(2, "Hello Universe", "akshay"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());

	}

	public Message getMessage(long id) throws DataNotFoundException {
		Message message = messages.get(id);

		if (message == null)
			throw new DataNotFoundException("There is no message with id: "
					+ id);

		return message;
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}

		messages.put(message.getId(), message);
		return message;
	}

	public List<Message> updateAllMessages(Message message) {
		Set<Entry<Long, Message>> entry = messages.entrySet();

		Iterator<Entry<Long, Message>> i = entry.iterator();

		while (i.hasNext()) {
			i.next().setValue(message);
		}

		return new ArrayList<Message>(messages.values());
	}

	public Message removeMessage(long id) {
		return messages.remove(id);
	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messageForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year)
				messageForYear.add(message);
		}

		return messageForYear;
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> messagesPaginated = new ArrayList<Message>();
		if (start + size > messagesPaginated.size())
			return new ArrayList<Message>();

		return messagesPaginated.subList(start, start + size);
	}

}
