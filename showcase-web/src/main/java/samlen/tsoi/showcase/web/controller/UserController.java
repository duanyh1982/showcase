package samlen.tsoi.showcase.web.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samlen.tsoi.showcase.common.pojo.dto.Result;
import samlen.tsoi.showcase.common.util.WebFileUtils;
import samlen.tsoi.showcase.web.entity.po.User;
import samlen.tsoi.showcase.web.service.UserReadService;
import samlen.tsoi.showcase.web.service.UserWriteService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author samlen_tsoi
 * @date 2017/12/1
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserWriteService userWriteService;

    @Autowired
    private UserReadService userReadService;

    @Autowired
    private HttpServletResponse response;

    /**
     * 创建
     * @param user
     */
    @PostMapping("add")
    public void create(@RequestBody User user) {
        userWriteService.insertOne(user);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页获取用户", notes = "这是notes")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "一页大小", required = true, dataType = "Integer")
    })
    @GetMapping("page")
    public PageInfo<User> page(@RequestParam("pageNum") Integer pageNum,
                               @RequestParam("pageSize") Integer pageSize) {
        return userReadService.page(pageNum, pageSize);
    }

    /**
     * 导出用户信息Excel
     *
     * @throws IOException
     */
    @GetMapping("exportExcel")
    public void exportExcel() throws IOException {
        //文件名
        String fileName = "用户信息-" + DateFormatUtils.format(new Date() , "yyyyMMddHHmmss");
        //告诉浏览器用什么软件可以打开此文件
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        //下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName,"UTF-8") + ".xls");
        //查询用户信息
        PageInfo<User> page = userReadService.page(1, 20);
        List<User> userList = page.getList();
        //Sheet参数
        Map<String, Object> sheetMap = new HashMap<String, Object>(3);
        //设置导出参数
        ExportParams params = new ExportParams();
        params.setSheetName("用户信息");
        //设置sheet参数
        sheetMap.put("title", params);
        sheetMap.put("entity", User.class);
        sheetMap.put("data", userList);
        //add到列表中
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(sheetMap);
        //调用导出方法
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        workbook.write(response.getOutputStream());
    }

    /**
     * 用户信息导入
     *
     * @param multipartFile
     */
    @PostMapping("importExcel")
    public Result importExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        //文件下载到本地
        File file = WebFileUtils.download2Local(multipartFile);
        log.info("文件路径-{}, 文件名-{}", file.getAbsolutePath(), file.getName());
        //导入
        ImportParams params = new ImportParams();
        //表头行数
        params.setHeadRows(1);
        List<User> list = ExcelImportUtil.importExcel(file, User.class, params);
        //打印
        list.forEach(user -> log.info("user-{}", user));
        return Result.ok(list);
    }
}
