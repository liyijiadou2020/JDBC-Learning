USE atguigudb;

# ORDER BY 排序
SELECT department_id, last_name, first_name, salary FROM employees ORDER BY department_id, salary DESC;

# 分页 LIMIT
# 显示前10条记录
SELECT * FROM employees LIMIT 0, 10;





