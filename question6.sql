## Find all users (user_id's) with email addresses ending with "gmail.com"##
# query 1)#
select s.user_id from site_user s where s.email_address like '%@gmail.com';

# query 2)#
select * from site_user where email_address like '%@gmail.com';


# b. Find all lineitems of invoice 2 and list their product id and quantity.#

select product_id, quantity  from lineitem where invoice_id = 2;

#c. List all the downloads (product id, filename, and date) for the user with email andi@murach.com.#
select product_id ,sample_filename, download_date from site_user s, download d, track t
where s.user_id =d.user_id  and  d.track_id =t.track_id  and s.email_address = 'andi@murach.com';

#d. There are no invoices in the tables in the original load, making it hard to test queries involving invoices.#
#Fix this problem by inserting an invoice with id =2 (dated 9/2/10, for user 2) with lineitems for products with product codes pf01 and jr01.  Show the three insert statements in your hw paper.#
INSERT into invoice VALUES (2,2, timestamp '2010-09-02 00:00:00', 14.95,'Y'); 
INSERT INTO lineitem (lineitem_id, invoice_id, product_id, quantity) VALUES (1,2,2,1); 
INSERT INTO lineitem (lineitem_id, invoice_id, product_id, quantity) VALUES (2,2,4,1);

# e. List all invoice ids and invoice dates for the user with email andi@murach.com.#

select invoice_id, INVOICE_DATE from site_user s, INVOICE i where s.user_id = i.user_id and s.email_address = 'andi@murach.com';

# f. Find all product ids purchased by the user with email andi@murach.com (i.e., find the lineitems)#

select product_id from site_user s, INVOICE i , lineitem l where s.user_id = i.user_id and i.invoice_id = l.invoice_id and s.email_address = 'andi@murach.com';

# g. Find all products (product codes) where a download preceded a purchase. Avoid duplicates in the result#

SELECT distinct product_code FROM download d1, lineitem l1, invoice i1, product p1 
where d1.user_id=i1.user_id and l1.invoice_id=i1.invoice_id and p1.product_id=l1.product_id and d1.download_date < i1.invoice_date;

# h. Find the downloaded file (.mp3 filename) that was downloaded the highest number of times.#


select t.sample_filename from track t, download d where d.track_id = t.track_id and t.sample_filename like '%.mp3%' 
group by t.track_id, t.sample_filename 
having count(*) IN (select max(count(*)) from download d1 group by d1.track_id);


#  i. Report on the number of downloads (total over all files for that CD) by CD product code, since August 1, 2009.#

select product_code, count(*) as Total from product p, track t, download d 
where p.product_id= t.product_id and t.track_id=d.track_id 
and d.download_date >= timestamp '2009-08-01 00:00:00.0'
group by p.product_id, p.product_code
