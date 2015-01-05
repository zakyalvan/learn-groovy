/**
 * This script showing that all java construct could be used in any groovy scripts.
 * 
 * Run this with VM arguments for self monitoring.
 * 
 * -Dcom.sun.management.jmxremote.port=3333
 * -Dcom.sun.management.jmxremote.authenticate=false
 * -Dcom.sun.management.jmxremote.ssl=false
 * 
 * @author zakyalvan
 */

import java.lang.management.ManagementFactory
import java.lang.management.MemoryMXBean

import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

def serviceUrl = 'service:jmx:rmi:///jndi/rmi://localhost:3333/jmxrmi';
def server = JMXConnectorFactory.connect(new JMXServiceURL(serviceUrl)).MBeanServerConnection;

println 'HEAP USAGE';
def mem = ManagementFactory.newPlatformMXBeanProxy(
	server, 
	ManagementFactory.MEMORY_MXBEAN_NAME, 
	MemoryMXBean.class);

def heapUsage = mem.heapMemoryUsage;
println "Memory usage 		: ${heapUsage.used}";

mem.gc();
heapUsage = mem.heapMemoryUsage;
println "Memory usage after GC	: ${heapUsage.used}"