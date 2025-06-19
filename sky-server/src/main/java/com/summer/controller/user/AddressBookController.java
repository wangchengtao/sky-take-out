package com.summer.controller.user;

import com.summer.context.BaseContext;
import com.summer.entity.AddressBook;
import com.summer.result.Result;
import com.summer.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/addressBook")
@Api(tags = "用户-地址簿")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;


    @GetMapping("list")
    @ApiOperation("地址簿列表")
    public Result<List<AddressBook>> list() {
        List<AddressBook> list = addressBookService.list(new AddressBook());

        return Result.success(list);
    }

    @PostMapping
    @ApiOperation("新增地址")
    public Result<Void> save(@RequestBody AddressBook addressBook) {
        addressBookService.save(addressBook);

        return Result.success();
    }


    @GetMapping("{id}")
    @ApiOperation("地址详情")
    public Result<AddressBook> getById(@PathVariable Long id) {

        AddressBook book = addressBookService.getById(id);
        return Result.success(book);
    }

    @PutMapping
    @ApiOperation("修改地址")
    public Result<Void> update(@RequestBody AddressBook addressBook) {
        addressBookService.update(addressBook);

        return Result.success();
    }

    @PutMapping("default")
    @ApiOperation("设置默认地址")
    public Result<Void> setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);

        return Result.success();
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据 id 删除地址")
    public Result<Void> deleteById(@PathVariable Long id) {
        addressBookService.deleteById(id);

        return Result.success();
    }

    @GetMapping("default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault() {
        AddressBook book = new AddressBook();
        book.setIsDefault(1);
        book.setUserId(BaseContext.getCurrentId());

        List<AddressBook> list = addressBookService.list(book);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }

}
