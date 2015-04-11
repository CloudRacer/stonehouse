# sets up a set of global variables that will be consumed by several functions on this script.
def setGlobalVariables():
	print ""
	print "#======================================================================================="
	print "# SETING Global Variables."
	print "#======================================================================================="
	global URL
	global USERNAME
	global PASSWORD
	global JMS_SERVER_NAME
	global JMS_MODULE_NAME
	
	PROTOCOL='t3'
	SERVER_NAME='localhost'
	PORT=7001
	URL=PROTOCOL+'://'+SERVER_NAME+':'+str(PORT)
	USERNAME='weblogic'
	PASSWORD='welcome1'
	JMS_SERVER_NAME='WM6JMSServer'
	JMS_MODULE_NAME='WM6_QUEUES'

def connectToServer():
	connect(USERNAME,PASSWORD,URL)
	
def disconnectFromServer():	
	disconnect()

def terminate():
	print ' '
	print ' '
	print ' '
	disconnectFromServer()
	exit()

def listDestinations():
	connectToServer()

	#Switch to the server runtime tree
	serverRuntime()

	servers = domainRuntimeService.getServerRuntimes();

	if (len(servers) > 0):
		for server in servers:
			jmsRuntime = server.getJMSRuntime();
			jmsServers = jmsRuntime.getJMSServers();

			typeColumnName = 'Type'.ljust(15);
			nameColumnName = 'Name'.ljust(60);
			currentColumnName = 'Current'.ljust(12);
			highCountColumnName = 'High Count'.ljust(12);
			pendingColumnName = 'Pending'.ljust(12);
			totalReceivedColumnName = 'Received'.ljust(12);
			print typeColumnName+nameColumnName+currentColumnName+highCountColumnName+pendingColumnName+totalReceivedColumnName
			print '-----------------------------------------------------------------------------------------------------------------------'

			for jmsServer in jmsServers:
				destinations = jmsServer.getDestinations();
				for destination in destinations:
					destinationName = destination.getName();
					destinationDetails = [
											 destination.getDestinationType().ljust(15)
											,destinationName.ljust(60)
											,str(destination.getMessagesCurrentCount()).ljust(12)
											,str(destination.getMessagesHighCount()).ljust(12)
											,str(destination.getMessagesPendingCount()).ljust(12)
											,str(destination.getMessagesReceivedCount()).ljust(12)
										 ]
					print(''.join(map(str,destinationDetails)))

try:
	setGlobalVariables()
	listDestinations()
	terminate()	
except Exception, e:
	print e
	print "ERROR:", sys.exc_info()[0]
	terminate()