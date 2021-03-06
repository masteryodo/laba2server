
package com.mycompany.laba2server.dto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order
{
    private final long orderId;
    private long clientId;  
    private Date orderDate;
    private double orderSum;
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public Order(long orderId, long clientId, Date orderDate, double orderSum)
    {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
    }

    @Override
    public String toString() {
        String res = String.valueOf(getOrderId()) + String.valueOf(getClientId()) + format.format(getOrderDate()) + getOrderSum();
        return res;
    }

    @Override
    public int hashCode() {
        return (String.valueOf(clientId) + orderDate + orderSum).hashCode(); 
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj)
        {
            return true;
        }
	if (obj == null)
        { 
            return false; 
        }
        if (getClass() != obj.getClass())
        { 
            return false; 
        }
        Order other = (Order) obj;
	if (!Long.valueOf(clientId).equals(other.clientId))
        {   
            return false;
        }
	if (!format.format(orderDate).equals(format.format(other.orderDate)))
        {
            return false;
        }
	if (orderSum != other.orderSum)
        {
            return false; 
        }
	return true;
    }

    public long getOrderId()
    {
        return this.orderId;
    }

    public long getClientId()
    {
        return clientId;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }

    public double getOrderSum()
    {
        return orderSum;
    }

    public void setClientId(long clientId)
    {   
        this.clientId = clientId;
    }

    public void setOrderDate(String orderDate) throws ParseException
    {   
        this.orderDate = format.parse(orderDate);
    }

    public void setOrderSum(double orderSum)
    {
        this.orderSum = orderSum;
    }
}
