## получить всех сотрудников

	GET http://localhost:8080/employees

	пример ответа:
	[
	    {
	        "id": 1,
	        "fullName": "Yastrebov Vadim",
	        "department": "office",
	        "orders": []
	    },
	    {
	        "id": 2,
	        "fullName": "Sidorov Sergey",
	        "department": "soft",
	        "orders": []
	    }
	]

## получить сотрудника по id

	GET http://localhost:8080/employees/{id}

	пример ответа:
	{
	    "id": 1,
	    "fullName": "Yastrebov Vadim",
	    "department": "office",
	    "orders": []
	}

## создать нового сотрудника

	POST http://localhost:8080/employees

	пример:
	{
	    "fullName": "Sidorov Sergey",
	    "department": "soft"
	}

## редактировать сотрудника

	PUT http://localhost:8080/employees/{id}

	пример:
	{
	    "fullName": "Sidorov Sergey",
	    "department": "office"
	}

## удалить сотрудника 

	DELETE http://localhost:8080/employees/{id}

## добавить заказ на сотрудника
	
	POST http://localhost:8080/orders
	
	пример:
	{
	    "name": "important order",
	    "furniture": "table",
	    "dueDate": "12-02-2021",
	    "employeeName": "Sidorov Sergey"
	}

	ответ:
	{
	    "id": 1,
	    "name": "important order",
	    "furniture": "table",
	    "creationDate": "24-11-2019",
	    "dueDate": "12-02-2021",
	    "employeeName": "Sidorov Sergey",
	    "department": null
	}

## добавить заказ на отдел

	POST http://localhost:8080/orders

	пример:

	{
	    "name": "another important order",
	    "furniture": "table",
	    "dueDate": "12-02-2022",
	    "department": "office"
	}

	ответ:
	{
	    "id": 2,
	    "name": "another important order",
	    "furniture": "table",
	    "creationDate": "24-11-2019",
	    "dueDate": "12-02-2022",
	    "employeeName": null,
	    "department": "office"
	}

	у всех сотрудников office теперь есть another important order:
	[
	    {
	        "id": 1,
	        "fullName": "Yastrebov Vadim",
	        "department": "office",
	        "orders": [
	            "another important order"
	        ]
	    },
	    {
	        "id": 2,
	        "fullName": "Sidorov Sergey",
	        "department": "office",
	        "orders": [
	            "another important order"
	        ]
	    }
	]

## автоматическое определение отдела по типу мебели в заказе

	POST http://localhost:8080/orders

	пример:
	{
	    "name": "order",
	    "furniture": "table",
	    "dueDate": "12-02-2022"
	}

	ответ:
	{
	    "id": 3,
	    "name": "order",
	    "furniture": "table",
	    "creationDate": "24-11-2019",
	    "dueDate": "12-02-2022",
	    "employeeName": null,
	    "department": "office"
	}

## обновление заказа

	PUT http://localhost:8080/orders/{id}

	пример:
	{
    "name": "renamed order",
    "furniture": "table",
    "dueDate": "12-02-2022"
	}

	ответ:
	{
        "id": 3,
        "name": "renamed order",
        "furniture": "table",
        "creationDate": "23-11-2019",
        "dueDate": "11-02-2022",
        "employeeName": null,
        "department": "office"
    }

## удаление заказа:

	DELETE http://localhost:8080/orders/{id}

## получить заказ по id

	http://localhost:8080/orders/{id}

## получить все заказы

	http://localhost:8080/orders

## получить незавершенные заказы

	GET http://localhost:8080/orders/unfinished

	пример ответа

	[
	    {
	        "id": 2,
	        "name": "another important order",
	        "furniture": "table",
	        "creationDate": "23-11-2019",
	        "dueDate": "11-02-2022",
	        "employeeName": null,
	        "department": "office"
	    },
	    {
	        "id": 3,
	        "name": "renamed order",
	        "furniture": "table",
	        "creationDate": "23-11-2019",
	        "dueDate": "11-02-2022",
	        "employeeName": null,
	        "department": "office"
	    }
	]

## получить заказы отдела

	GET http://localhost:8080/orders/department/{departmentName}

	пример: GET http://localhost:8080/orders/department/office

	ответ:
	[
	    {
	        "id": 2,
	        "name": "another important order",
	        "furniture": "table",
	        "creationDate": "23-11-2019",
	        "dueDate": "11-02-2022",
	        "employeeName": null,
	        "department": "office"
	    },
	    {
	        "id": 3,
	        "name": "renamed order",
	        "furniture": "table",
	        "creationDate": "23-11-2019",
	        "dueDate": "11-02-2022",
	        "employeeName": null,
	        "department": "office"
	    }
	]

## получить заказы сотрудника 

	GET http://localhost:8080/orders/employee/{employeeName}

	пример:http://localhost:8080/orders/employee/Sidorov Sergey

	ответ:
	[
	    {
	        "id": 4,
	        "name": "order",
	        "furniture": "table",
	        "creationDate": "23-11-2019",
	        "dueDate": "11-02-2021",
	        "employeeName": "Sidorov Sergey",
	        "department": null
	    }
	]

## узнать количество дней до завершения заказа

	GET http://localhost:8080/orders/time/{id}

	пример GET http://localhost:8080/orders/time/1
	ответ: 810 days



