本项目是对[一套基于SpringBoot-shiro-vue的权限管理思路](https://github.com/Heeexy/SpringBoot-Shiro-Vue)的后端代码学习，主要学习了按钮级别的权限控制思想。<br>
一点小笔记：https://blog.csdn.net/qq_40167974/article/details/113985009 <br><br>
本地运行：
- 数据库中导入.sql文件
- 修改.property文件中的数据库用户名和密码

由于只有后端代码，所以在此提供api以供Postman做测试：

#### 一、  用户登录

- **用户登录**

  - `Api:[POST]http://localhost:8989/permission/login/auth `

  - `Body:`

    ```json
    {
        "username":"admin",
        "password":"123456"
    }
    ```

- **用户授权**

  - `Api:[POST]http://localhost:8989/permission/login/getInfo `

#### 二、 文章管理

- **文章列表**

  - `Api:[GET]http://localhost:8989/permission/article/list `

- **添加文章**

  - `Api:[POST]http://localhost:8989/permission/article/add `

  - `Body:`

    ```json
    {
        "content":"hey"
    }
    ```

- **修改文章**

  - `Api:[POST]http://localhost:8989/permission/article/update `

  - `Body:`

    ```json
    {
        "id":"22",
        "content":"blackmamba"
    }
    ```

- **删除文章**

  - `Api:[POST]http://localhost:8989/permission/article/delete `

  - `Body:`

    ```json
    {
        "id":"22"
    }
    ```

#### 三、用户管理

- **用户列表**

  - `Api:[GET]http://localhost:8989/permission/user/listUser `

- **添加用户**

  - `Api:[POST]http://localhost:8989/permission/user/addUser `

  - `Body:`

    ```json
    {
        "username":"fanta",
        "nickname":"stillFanta",
        "password":"fanta",
        "roleId":"3"
    }
    ```

- **修改用户**

  - `Api:[POST]http://localhost:8989/permission/user/updateUser `

  - `Body:`

    ```json
    {
        "userId":"10008",
        "nickname":"fantaStill",
        "roleId":"3",
        "deleteStatus":"1"
    }
    ```

- **删除用户**

  - `Api:[POST]http://localhost:8989/permission/user/deleteUser `

  - `Body:`

    ```json
    {
        "userId":"10008"
    }
    ```

#### 四、角色管理

- **角色列表**

  - `Api:[GET]http://localhost:8989/permission/user/listRole `

- **添加角色**

  - `Api:[POST]http://localhost:8989/permission/user/addRole `

  - `Body:`

    ```json
    {
        "roleName":"无名小卒",
        "permissions":[101,601,701,102]
    }
    ```

- **修改角色**

  - `Api:[POST]http://localhost:8989/permission/user/updateRole `

  - `Body:`

    ```json
    {
        "roleId":"8",
        "roleName":"只有列表",
        "permissions":[101,601,701]
    }
    ```

- **删除角色**

  - `Api:[POST]http://localhost:8989/permission/user/deleteRole `

  - `Body:`

    ```json
    {
        "roleId":"8"
    }
    ```

    
