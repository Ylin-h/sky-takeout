package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工管理")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    @PostMapping
    @ApiOperation("新增员工")
    public Result add(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.add(employeeDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("查询员工列表")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("查询员工列表：{}", employeePageQueryDTO);
        return Result.success(employeeService.pageQuery(employeePageQueryDTO));
    }
    @PostMapping("/status/{status}")
    @ApiOperation("修改员工状态")
    public Result stopOrStart(@PathVariable("status") Integer status, Long id) {
        log.info("修改员工状态：status={}, id={}", status, id);
        employeeService.update(status, id);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("查询员工详情")
    public Result<Employee> getById(@PathVariable("id") Long id) {
        log.info("查询员工详情：id={}", id);
        return Result.success(employeeService.getById(id));
    }
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("修改员工信息：{}", employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除员工")
    public Result delete(@PathVariable("id") Long id) {
        log.info("删除员工：id={}", id);
        employeeService.delete(id);
        return Result.success();
    }
}
