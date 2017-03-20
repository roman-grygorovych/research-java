CREATE TABLE department (
  id INTEGER PRIMARY KEY,
  name VARCHAR(30)
);

CREATE TABLE employee (
  id INTEGER PRIMARY KEY,
  nick VARCHAR(30),
  salary DECIMAL,
  birthdate DATE,
  department_id INTEGER
);