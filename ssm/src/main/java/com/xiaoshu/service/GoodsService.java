package com.xiaoshu.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.GoodsVo;
import com.xiaoshu.entity.Goodstype;

import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class GoodsService {
@Autowired
private GoodsMapper goodsMapper;
	
			public PageInfo<GoodsVo> findPage(GoodsVo goodsVo,Integer pageNum,Integer pageSize){
				PageHelper.startPage(pageNum, pageSize);
				List<GoodsVo>list = goodsMapper.findList(goodsVo);
				return new PageInfo<>(list);
			}
			
			
			
			
			
			
			
			
			
			
	@Autowired 
	GoodstypeMapper goodstypeMapper;
	public List<Goodstype>findGoodstype(){
		return goodstypeMapper.selectAll();
		
	}
	
	
	
	
	
	
	
	
	
	
	public Goods findName(String Name) {
		Goods param = new Goods();
		param.setName(Name);
		return goodsMapper.selectOne(param);
		
	}
	public void addGoods(Goods goods){
		goods.setCreatetime(new Date());
		goodsMapper.insert(goods);
		
	}
	public void deleteGoods(int id){
		goodsMapper.deleteByPrimaryKey(id);
	}
	public void updateGoods(Goods goods){
		goodsMapper.updateByPrimaryKeySelective(goods);
	}
	
	
	
	
	
	public List<GoodsVo>findList(GoodsVo goodsVo){
		return goodsMapper.findList(goodsVo);
	}
	
	public void importGoods(MultipartFile goodsFile) throws InvalidFormatException, IOException{
		Workbook workbook = WorkbookFactory.create(goodsFile.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		for (int i = 0; i < rowNum; i++) {
			Row row = sheet.getRow(i+1);
			
			String code = row.getCell(0).toString();
			String name = row.getCell(1).toString();
			String tname = row.getCell(2).toString();
			String price = row.getCell(3).toString();
			String status = row.getCell(4).toString();
			Date createtime = row.getCell(5).getDateCellValue();
			
			
			Goods g = new Goods();
			g.setCode(code);
			g.setName(name);
			
			g.setPrice(price);
			g.setStatus(status);
			g.setCreatetime(new Date());
			
			
			
			Goodstype param = new Goodstype();
			param.setTypename(tname);
			Goodstype goodstype = goodstypeMapper.selectOne(param);
			g.setTypeid(goodstype.getId());
			
			goodsMapper.insert(g);
		}
	}
	
}
