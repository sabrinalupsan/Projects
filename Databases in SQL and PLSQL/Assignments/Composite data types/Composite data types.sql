SET SERVEROUTPUT ON
DECLARE 
    V_STRING VARCHAR2(100):='25925624594564';
    TYPE t_strings is table of number index by pls_integer;
    v t_strings;
    i pls_integer;
BEGIN
    FOR i in 1..length(v_string) LOOP
        v(i):=substr(v_string, i, 1);
    END LOOP;
    dbms_output.put_line('The collection has '||v.count||' elements.');
    v.delete(3);
    dbms_output.put_line('The collection has '||v.count||' elements.');
END;
/
DECLARE 
    V_STRING VARCHAR2(100):='25925624594564';
    TYPE t_strings is table of number index by pls_integer;
    v t_strings;
    i PLS_INTEGER :=1;
BEGIN
    while i<=length(v_string) LOOP
        v(i):=substr(v_string, i, 1);
        i:=i+1;
    END LOOP;
    dbms_output.put_line('The collection has '||v.count||' elements.');
    v.delete(3);
    dbms_output.put_line('The collection has '||v.count||' elements.');
END;
/
DECLARE 
    type t_nt is table of number;
    v t_nt;
    i PLS_INTEGER:=1;
BEGIN
    v:=t_nt();
    v.extend(1);
    v(1):=10;
    dbms_output.put_line('Number of elements: '||v.count);
    v.extend(7);
    dbms_output.put_line('Number of elements: '||v.count); 
    v(8):=120;
    v.delete(2,6);
    while i<=v.last loop
        if v(i) is not null then
            dbms_output.put_line(i||'->'||v(i));   
        end if;
        i:=v.next(i);
    end loop;
END;
/
DECLARE 
    type t_nt is table of number;
    v t_nt;
    i PLS_INTEGER;
BEGIN
    v:=t_nt();
    v.extend(1);
    v(1):=10;
    dbms_output.put_line('Number of elements: '||v.count);
    v.extend(7);
    dbms_output.put_line('Number of elements: '||v.count);
    v(8):=120;
    v.delete(2,7);
    for i in v.first..v.last loop
        if v.exists(i) then
            dbms_output.put_line(i||'->'||v(i));   
        end if;
    end loop;
END;
/
SET SERVEROUTPUT ON
DECLARE
TYPE tab_tari IS VARRAY(10) OF countries.country_name%type;
TYPE tab_produse IS table of product_information%rowtype;
v tab_tari;
p tab_produse; 
BEGIN
 SELECT country_name BULK COLLECT INTO v FROM countries WHERE REGION_ID=1;
 for i in 1..v.count loop
      dbms_output.put_line(v(i));
 end loop;    
 
 delete from PRODUCT_INFORMATION where product_id not in (select product_id from order_items)
 returning product_id,product_name, product_description, category_id, weight_class, supplier_id, 
        product_status, list_price, min_price, catalog_url
        BULK COLLECT INTO p;
 
 dbms_output.put_line('There were '||p.count||' deleted products');
 
 for i in p.first..p.last loop
     dbms_output.put_line('The name of the deleted product is '||p(i).product_name||' and the category ID is '||p(i).category_id);
end loop;
rollback;
END;
/
