package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);
/**
 * 员工新增
 * @param employeeDTO
 */
    void add(EmployeeDTO employeeDTO);
/**
 * 员工分页查询
 * @param employeePageQueryDTO
 * @return
 */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
/**
 * 员工停用或启用
 * @param status
 * @param id
 * @return
 */
    void stopOrStart(Integer status, Long id);
/**
 * 员工删除
 * @param id
 */
    void delete(Long id);
/**
 * 员工详情
 * @param id
 * @return
 */
    Employee getById(Long id);
/**
 * 员工修改
 * @param employeeDTO
 */
    void update(EmployeeDTO employeeDTO);
}
