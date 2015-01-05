/**
 * Script for following dsl :
 * send message with subject 'ble ble ble' and content 'bla bla bla' to user with id 2 by email
 */
class Message {
	
}
 
class MessageFactory {
	static Message message() {
		
	}
}

class MessageSender {
	def send(Message message) {
		
	}
}

send message(subject :'ble ble ble', content :'bla bla bla'),
	to(user(id : 1)),
	by(email)