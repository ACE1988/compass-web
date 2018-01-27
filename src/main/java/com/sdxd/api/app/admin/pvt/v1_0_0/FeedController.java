package com.sdxd.api.app.admin.pvt.v1_0_0;

import com.sdxd.api.service.FeedService;
import com.sdxd.api.vo.admin.Feed;
import com.sdxd.api.vo.admin.FeedWithoutId;
import com.sdxd.api.vo.admin.QueryFeedList;
import com.sdxd.common.web.vo.ProcessBizException;
import com.sdxd.common.web.vo.RestResponse;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import static com.sdxd.common.web.vo.RestResponse.ok;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.api.app.admin.pvt.v1_0_0
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 2017/8/7    wenzhou.xu              Created
 */
@Api(value = "Feed-API", produces = "application/json")
@Controller
@RequestMapping(value = "/feed", produces = "application/json")
public class FeedController {

    @Resource
    private FeedService feedService;

    @ApiOperation(value = "查询Feed素材列表", notes = "查询Feed素材列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> search(@Valid @BeanParam QueryFeedList query) {
        try{
            return ok(feedService.searchCmsFeedList(query));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    @ApiOperation(value = "获取Feed素材明细", notes = "获取Feed素材明细")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> queryFeed(@PathVariable("id") String id) {
        try{
            return ok(feedService.queryCmsFeed(id));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }
    }

    //新增Feed素材
    @ApiOperation(value = "新增Feed素材", notes = "新增Feed素材")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Object> insert(@Valid @RequestBody FeedWithoutId feedWithoutId) {
        try{
            return ok(feedService.saveCmsFeed(feedWithoutId));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }

    }

    //编辑Feed素材
    @ApiOperation(value = "编辑Feed素材", notes = "编辑Feed素材")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<Object> update(@Valid @RequestBody Feed feed) {
        try{
            return ok(feedService.saveCmsFeed(feed));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }

    }

    //删除Feed素材
    @ApiOperation(value = "删除Feed素材", notes = "删除Feed素材")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = "Authorization",
                    value = "用户Token",
                    dataType = "string",
                    paramType = "header",
                    example = "Bearer 0b79bab50daca910b000d4f1a2b675d604257e42",
                    required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")
    })
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse<Object> delete(@RequestParam String id) {
        try{
            return ok(feedService.deleteCmsFeed(id));
        }
        catch (ProcessBizException e){
            return e.toResult();
        }

    }
}
