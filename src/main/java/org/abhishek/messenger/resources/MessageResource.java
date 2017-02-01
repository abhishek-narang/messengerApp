package org.abhishek.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.abhishek.messenger.beans.MessageFilterBean;
import org.abhishek.messenger.model.Message;
import org.abhishek.messenger.service.MessageService;

/**
 * MessageResource exposed at webapi/messages
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();

    /**
     * Method handling HTTP GET requests at webapi/messages. 
     * The returned object will be sent to the client as "application/json" media type.
     *
     * @return List<Message>
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONMessages(@BeanParam MessageFilterBean bean) {
		System.out.println("JSON method called");
		if (bean.getYear() > 0)
			return messageService.getAllMessagesForYear(bean.getYear());

		if (bean.getStart() > 0 && bean.getSize() > 0)
			return messageService.getAllMessagesPaginated(bean.getStart(),
					bean.getSize());

		return messageService.getAllMessages();
	}

    /**
     * Method handling HTTP GET requests at webapi/messages. 
     * The returned object will be sent to the client as "text/xml" media type.
     *
     * @return List<Message>
     */
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXMLMessages(@BeanParam MessageFilterBean bean) {
		System.out.println("XML method called");
		if (bean.getYear() > 0)
			return messageService.getAllMessagesForYear(bean.getYear());

		if (bean.getStart() > 0 && bean.getSize() > 0)
			return messageService.getAllMessagesPaginated(bean.getStart(),
					bean.getSize());

		return messageService.getAllMessages();
	}

    /**
     * Method handling HTTP GET requests at webapi/messages/messageId. 
     * The returned object will be sent to the client as "application/json" media type.
     * @param  url  an absolute URL giving the base location of the image
     * @param  name the location of the image, relative to the url argument
     * @return List<Message>
     */
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id,
			@Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");

		return message;

	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId()).build();
		return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder().path(ProfileResource.class)
				.path(message.getAuthor()).build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(Long.toString(message.getId())).build().toString();
		return uri;
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo)
			throws URISyntaxException {

		Message addMessage = messageService.addMessage(message);
		// uriInfo.getAbsolutePath()
		String id = String.valueOf(addMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

		return Response.created(uri).entity(addMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId,
			Message message) {
		message.setId(messageId); // setting the id of the message to be updated
									// obtained from URI of PUT request
		return messageService.updateMessage(message);
	}

	@PUT
	public List<Message> updateAllMessages(Message message) {
		return messageService.updateAllMessages(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long messageId) {
		messageService.removeMessage(messageId);
	}

	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}

}
