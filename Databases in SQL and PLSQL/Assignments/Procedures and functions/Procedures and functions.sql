set SERVEROUTPUT on;
--Build a procedure that receives by parameter a calendar year (e.g., 2018). 
--The salaries of employees who brokered at least 3 orders in that year will be increased by 10%. 
--The number of increased salaries will be returned by a parameter. 
--If no employee has brokered orders in that year, an exception will be raised. 
--The procedure will be called from an anonymous block where the exception raised in the procedure will be dealt with.
create or replace procedure sal(p_year IN NUMBER, p_number OUT NUMBER) is
p_number2 number :=NULL;
BEGIN
update employees
set salary = salary*1.1
where employee_id in 
(select sales_rep_id
from orders 
where extract(year from order_date) = p_year
group by sales_rep_id
having count(sales_rep_id) > 2);
p_number:=SQL%ROWCOUNT;
select sales_rep_id into p_number2 from orders where extract(year from order_date) = p_year and rownum = 1 and sales_rep_id is not null group by sales_rep_id;
if p_number2 is null THEN 
    raise no_data_found;
end if;
END;
/

DECLARE
no_sal number;
BEGIN
sal(p_year=>2019, p_number=>no_sal);
dbms_output.put_line(no_sal);
exception when no_data_found then
    dbms_output.put_line('No employee brokered an order in the year selected.');
END;
/
--Build a function that receives a product's id and deletes that product. 
--If that product cannot be deleted because it appears on the orders, those rows (order_items) will be deleted first.  
--The function will return YES if the product exists and has been deleted and NO otherwise. 
--Call the function from an anonymous block.
create or replace function delete_records(p_product_id number) return string is
child_record exception;
pragma exception_init(child_record, -02292);
BEGIN
delete 
from product_information
where product_id = p_product_id;
IF SQL%NOTFOUND THEN
    RETURN 'NO';
else
    RETURN 'YES';
END IF;
exception when child_record then

    DELETE
    FROM order_items
    WHERE product_id = p_product_id;
    
    DELETE 
    FROM product_information
    WHERE product_id = p_product_id;
    
IF SQL%NOTFOUND THEN
    RETURN 'NO';
else
    RETURN 'YES';
END IF;
END;
/

BEGIN
    dbms_output.put_line(delete_records(3133));
END;
/