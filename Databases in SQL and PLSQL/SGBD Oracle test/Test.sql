--Construct a package (spec+body) that contains: 
--1. A function that takes in a parameter: the customer’s ID, and returns the date of the customer’s latest order. 
--Call the function from a SQL statement inside an anonymous block.
--2. A procedure that displays the first and last names of the employees who were hired after a date passed with a parameter. 
--Raise an error in the procedure if there are less than three employees hired after that date. 
--Call the procedure from an anonymous block.
set serveroutput on


create or replace package test_package is
function test_function(p_cust_id number) return date;
procedure test_proc(p_date date);
end;
/

create or replace package body test_package is
function test_function(p_cust_id number) return date is
cursor c is select order_date from orders where customer_id = p_cust_id order by order_date desc;
t c%rowtype;
i date;
j number:=0;

begin
for t in c loop
    j:=1;
    i:=t.order_date;
    exit when j=1;
end loop;
return i;
end;

procedure test_proc(p_date date) is
cursor c is select first_name, last_name from employees where hire_date>p_date;
t c%rowtype;
i number:=0;
begin
for t in c loop
    i:=i+1;
end loop;
if i < 3 then
    raise_application_error(-20001, 'It''s an error. Less than 3 employees.');
end if;
for t in c loop
    dbms_output.put_line(t.first_name||' '||t.last_name);
end loop;
end;
end;
/


declare 
i date;
begin
select test_package.test_function(customer_id) into i from customers where customer_id = 104;
dbms_output.put_line(i);
end;
/


--works, 3 employees
begin 
test_package.test_proc('23-DEC-19');
end;
/

--error, only 2 employees
begin 
test_package.test_proc('24-DEC-19');
end;
/