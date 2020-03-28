CREATE TABLE SmsNotification
{
smsNotification_id INTEGER PRIMARY KEY,
phone varchar (255)
};

CREATE TABLE EmailNotification
{
emailNotification_id INTEGER PRIMARY KEY,
phone varchar (255)
};

CREATE TABLE Notification
{
notification_id INTEGER PRIMARY KEY,
createdOn varchar (255),
content varchar (255)
};

CREATE TABLE Shipment
{
shipment_id INTEGER PRIMARY KEY,
shipmentNumber varchar (255),
shipmentDate varchar (255),
estimatedArrival varchar (255),
ShipmentMethod varchar (255)
};

CREATE TABLE ShipmentLog
{
shipmentLog_id INTEGER PRIMARY KEY,
shipmentNumber varchar (255),
status varchar (255),
creationDate varchar (255)
};

CREATE TABLE Order
{
order_id INTEGER PRIMARY KEY,
orderNumber varchar (255),
status varchar (255),
orderDate varchar (255),
};

CREATE TABLE OrderLog
{
orderLog_id INTEGER PRIMARY KEY,
orderNumber varchar (255),
creationDate varchar (255),
status varchar (255)
};

CREATE TABLE Payment
{
payment_id INTEGER PRIMARY KEY,
status varchar (255),
amount DOUBLE ,
};