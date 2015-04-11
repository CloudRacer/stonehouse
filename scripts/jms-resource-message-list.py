#=======================================================================================
# Import library
#=======================================================================================
from weblogic.jms.extensions import JMSMessageInfo
from javax.jms import TextMessage
from javax.jms import ObjectMessage

#=======================================================================================
# STANDARD FUNCTION DEFINITIONS
#=======================================================================================

# get queue name from the operator.
def chooseJMSQueueName():
	# with several options Ask the user for a choice
	global JMS_DESTINATION_NAME
	
	print ("ALL MESSAGES WILL BE RETRIEVED. This may take some time and/or represent a LARGE AMOUNT OF DATA.")
	JMS_DESTINATION_NAME = raw_input("Please enter the name of a JMS Queue to retrieve Messages from (e.g. 'Conv10AMSendQueue'): ")

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

def listJMSQueueMessages():
	connectToServer()

	#Switch to the server runtime tree
	serverRuntime()

	servers = domainRuntimeService.getServerRuntimes();
	
	#Navigate to the JMS Destination Runtime MBean
	cd('JMSRuntime/' + serverName + '.jms/JMSServers/' + JMS_SERVER_NAME)
	cd('Destinations/' + JMS_MODULE_NAME + '!' +  JMS_DESTINATION_NAME)

	#Get the cursor (JMSMessageCursorRuntimeMBean) to browse the messages - No selector & No time out
	cursor = cmo.getMessages('',0)

	#Determine the number of messages in the destination
	cursorsize = cmo.getCursorSize(cursor)
	print '------------------------------------------'
	print 'Total Number of Messages -> ', cursorsize
	print '------------------------------------------'

	#Get all the messages as an array of javax.management.openmbean.CompositeData
	messages = cmo.getNext(cursor, cursorsize)

	#Loop through the array of messages to print
	if (cursorsize > 0):
		if (len(servers) > 0):
			for message in messages:

				#Create WebLogic JMSMessageInfo to get Message ID
				jmsmsginfo = JMSMessageInfo(message)
				wlmsg = jmsmsginfo.getMessage()
				wlmsgid = wlmsg.getJMSMessageID()

				#Get Message with body
				fullcursormsg = cmo.getMessage(cursor,wlmsgid)
				fulljmsmsginfo = JMSMessageInfo(fullcursormsg)
				handle = fulljmsmsginfo.getHandle()
				compdata = cmo.getMessage(cursor, handle)
				msgwithbody = JMSMessageInfo(compdata)

				#Print Key Message Headers
				print 'Message ID           - ' + msgwithbody.getMessage().getJMSMessageID()
				print 'Message Priority     -' , msgwithbody.getMessage().getJMSPriority()
				if msgwithbody.getMessage().getJMSRedelivered() == 0:
					redeliv = 'false'
				else:
					redeliv = 'true'
				print 'Message Redelivered  - ' + redeliv
				print 'Message TimeStamp    -' , msgwithbody.getMessage().getJMSTimestamp()
				print 'Message DeliveryMode -' , msgwithbody.getMessage().getJMSDeliveryMode()

				#Print Message Properties
				prop_enum = msgwithbody.getMessage().getPropertyNames()
				print ' '
				print 'Message Properties   :'
				print ' '
				for prop in prop_enum:
					print prop + ' - > ' + msgwithbody.getMessage().getStringProperty(prop)

				#Print Message Body
				fullwlmsg = fulljmsmsginfo.getMessage()
				print ' '
				print 'Message Body         :'
				print ' '
				if isinstance(fullwlmsg, TextMessage):
					print fullwlmsg.getText()
				else:
					if isinstance(fullwlmsg, ObjectMessage):
						print fullwlmsg.getObject()
					else:
						print '***Not a Text or Object Message***'
						print fullwlmsg.toString()
				print ' '
				print '--------------------------------------------------------------'
				print ' '

			#Close cursor as No Time Out specified - Best practice
			cmo.closeCursor(cursor)

try:
	chooseJMSQueueName()
	setGlobalVariables()
	listJMSQueueMessages()
	terminate()	
except Exception, e:
	print e
	print "ERROR:", sys.exc_info()[0]
	terminate()