package asia.lhweb.IntelligentCard.mapper;

import asia.lhweb.IntelligentCard.model.dto.CyCardDTO;
import asia.lhweb.IntelligentCard.model.pojo.CyCard;

import java.util.List;

/**
* @author Administrator
* @description 针对表【cy_card】的数据库操作Mapper
* @createDate 2024-04-10 10:02:11
* @Entity asia.lhweb.IntelligentCard.model.pojo.CyCard
*/
public interface CyCardMapper {
    /**
     * 按卡号选择
     *
     * @param cyCardDTO 赛门铁克
     * @return {@link List}<{@link CyCard}>
     */
    List<CyCard> selectByCyCardDTO(CyCardDTO cyCardDTO);

}




