set SERVEROUTPUT on
--Display the id and the date of each order. Under each order will be displayed the list of products from that order, the (discount) price, the quantity as well as the total value of the order.
declare 
cursor d is (select * from orders);
cursor e(p_order_id number) is select * from order_items where order_id=p_order_id;
cursor f(p_product_id number) is select * from product_information where p_product_id = product_id;
r d%rowtype;
s e%rowtype;
t f%rowtype;
v_sum number(10,2);
begin
v_sum:=0;
for r in d loop
    dbms_output.put_line('Order '||r.order_id||' from '||r.order_date);
    for s in e(r.order_id) loop
        for t in f(s.product_id) loop
            dbms_output.put_line('  Product '||t.product_name||', price: '||s.discount_price||', quantity: '||s.quantity);
            v_sum := v_sum + t.list_price;
        end loop;
    end loop;
    dbms_output.put_line('The total value of the order '||r.order_id||' is '||v_sum);
    v_sum:=0;
end loop;
end;
/
--Using a cursor display the product_names of the products that haven’t been ordered so far and decrease by 15% the list and min price of these products
declare
cursor c is 
select p.product_id, product_name, list_price, min_price
from product_information p, order_items o
where p.product_id not in (select product_id from order_items)
group by p.product_id, product_name, list_price, min_price; --103 products
r c%rowtype;
begin
for r in c loop
    dbms_output.put_line('Product '||r.product_name);
    update product_information
    set min_price = 0.85*min_price where r.product_id = product_id;
    update product_information
    set list_price = 0.85*list_price where r.product_id = product_id;
end loop;
end;
/
