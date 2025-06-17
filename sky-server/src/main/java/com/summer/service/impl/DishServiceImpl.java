package com.summer.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.summer.constant.MessageConstant;
import com.summer.constant.StatusConstant;
import com.summer.dto.DishDTO;
import com.summer.dto.DishPageQueryDTO;
import com.summer.entity.Dish;
import com.summer.entity.DishFlavor;
import com.summer.execption.DeletionNotAllowedException;
import com.summer.mapper.DishFlavorMapper;
import com.summer.mapper.DishMapper;
import com.summer.mapper.SetmealMapper;
import com.summer.result.PageResult;
import com.summer.service.DishService;
import com.summer.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        dishMapper.insert(dish);

        // 获取 insert 语句生成的主键值
        Long id = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(id);
            });

            dishFlavorMapper.insertBatch(flavors);
        }
    }

    @Override
    public PageResult<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());

        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);

            // 是否起售
            if (Objects.equals(dish.getStatus(), StatusConstant.ENABLE)) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 是否被关联
        List<Long> setmealIds = setmealMapper.getSetmealIdsByDishIds(ids);

        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish = dishMapper.getById(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);

        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 修改基本信息
        dishMapper.update(dish);

        // 删除原有口味
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        // 重新插入口味
        dishDTO.getFlavors().forEach(dishFlavor -> {
            dishFlavor.setDishId(dishDTO.getId());
        });

        dishFlavorMapper.insertBatch(dishDTO.getFlavors());
    }

    @Override
    public List<DishVO> listWithFlavor(Dish dish) {
        List<Dish> dishList = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();

        for (Dish d : dishList) {
            DishVO vo = new DishVO();
            BeanUtils.copyProperties(d, vo);

            List<DishFlavor> flavors = dishFlavorMapper.getByDishId(d.getId());

            vo.setFlavors(flavors);
            dishVOList.add(vo);
        }

        return dishVOList;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();

        dishMapper.update(dish);
    }
}
