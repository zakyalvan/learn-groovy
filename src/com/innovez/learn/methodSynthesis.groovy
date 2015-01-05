/**
 * How to intercept method call and for missing method add method dynamically.
 * 
 * @author zakyalvan
 */
class SamplePerson {
	def plays = ['Tennis', 'Football']
	
	def work() {
		println 'working...'
	}
	def play() {
		println 'playing...'
	}
	
	def methodMissing(String name, args) {
		println "Method missing called for ${name}"
		if(name.startsWith('play')) {
			def gameInList = plays.find() {it->
				it.equals(name.split('play')[1])
			}
			
			if(gameInList) {
				println "Playing ${name.split('play')[1]}..."
			}
			else {
				throw new MissingMethodException(name, SamplePerson, args);
			}
		}
		else {
			throw new MissingMethodException(name, SamplePerson, args);
		}
	}
}

def zaky = new SamplePerson()
zaky.work()
zaky.playTennis()
zaky.playTennis()

/**
 * Intercept missing method call, add method dynamically 
 * and cache method (add to meta-class) for next call.
 * 
 * @author zakyalvan
 */
class OtherSamplePerson {
	def plays = ['Tennis', 'Football']
	
	def work() {
		println 'Working...'
	}
	
	def methodMissing(String methodName, Object args) {
		println "Method missing called for ${methodName}"
		
		if(methodName.startsWith('play')) {
			def gameInList = plays.find() {it ->
				it.equals(methodName.split('play')[1])
			}
			
			if(gameInList) {
				// Create and cache method.
				def closure = {Object[] vargs ->
					println "Playing ${methodName.split('play')[1]}..."
				}
				
				// This seem awkward, i can't access metaclass directly using this.
				((OtherSamplePerson) this).metaClass."${methodName}" = closure
				
				// Last one, call to new added method.
				this."${methodName}"(args)
			}
			else {
				throw new MissingMethodException(methodName, OtherSamplePerson, args);
			}
		}
		else {
			throw new MissingMethodException(methodName, OtherSamplePerson, args);
		}
	}
}

def radhy = new OtherSamplePerson()
radhy.work()
radhy.playTennis()
radhy.playFootball()
radhy.playTennis()
radhy.playFootball()

/**
 * Same as example before, but with method call intercepted.
 * 
 * @author zakyalvan
 */
class InterceptablePerson implements GroovyInterceptable {
	def plays = ['Tennis', 'FootBall']
	
	def work() {
		System.out.println "Working..."
	}
	
	@Override
	public Object invokeMethod(String name, Object args) {
		System.out.println "Method call intercepted for ${name}..."
		MetaMethod metaMethod = metaClass.getMetaMethod(name, args)
		if(metaMethod != null) {
			return metaMethod.invoke(this, args)
		}
		else {
			return metaClass.invokeMethod(this, name, args)
		}
	}
	public Object methodMissing(String name, Object args) {
		System.out.println "Method missing called for ${name}..."
		if(name.startsWith('play')) {
			def gameInList = plays.find{it ->
				it.equals(name.split('play')[1])
			}
			
			if(gameInList) {
				def closure = {vargs ->
					System.out.println "Playing ${name.split('play')[1]}"
				}
				
				((InterceptablePerson) this).metaClass."${name}" = closure
				((InterceptablePerson) this)."${name}"(args)
			}
			else {
				throw new MissingMethodException(name, InterceptablePerson, args);
			}
		}
		else {
			throw new MissingMethodException(name, InterceptablePerson, args);
		}
	}
}

def interceptablePerson = new InterceptablePerson();
interceptablePerson.work()
interceptablePerson.playTennis()
interceptablePerson.playTennis()
interceptablePerson.playBadminton()
interceptablePerson.playTennis()

/**
 * In case we can't implements methodMissing directly on required type,
 * we can define method on type's metaclass (expando meta class)
 * 
 * @author zakyalvan
 */
class AnotherSamplePerson {
	def work() {
		println 'Working...'
	}
}

AnotherSamplePerson.metaClass.methodMissing = {String methodName, Object args ->
	def plays = ['Tennis', 'Football']
	
	println "Method missing called for ${methodName}..."
	
	
}