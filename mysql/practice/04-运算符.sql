
USE atguigudb;

# LIKE 运算符
# 查询 S 开头的 first name
SELECT first_name FROM employees WHERE first_name LIKE 'S%';
# 查询 last_name 第二个字符是o的员工
SELECT last_name FROM employees WHERE last_name LIKE '_o%';



