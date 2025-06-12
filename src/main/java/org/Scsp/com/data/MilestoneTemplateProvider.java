package org.Scsp.com.data;

import org.Scsp.com.model.MilestoneTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class MilestoneTemplateProvider {

    public List<MilestoneTemplate> getTemplates() {
        List<MilestoneTemplate> templates = List.of(
                new MilestoneTemplate(
                        "Ổn định nhịp tim",
                        "Chỉ sau 20 phút, nhịp tim và huyết áp bắt đầu trở lại mức bình thường.",
                        Duration.ofMinutes(20)
                ),
                new MilestoneTemplate(
                        "Giảm CO trong máu",
                        "Sau 8 giờ, lượng carbon monoxide (CO) trong máu giảm, tăng lượng oxy trong cơ thể.",
                        Duration.ofHours(8)
                ),
                new MilestoneTemplate(
                        "Hàm lượng oxy ổn định",
                        "Lượng oxy trong máu được cải thiện đáng kể sau 12 giờ.",
                        Duration.ofHours(12)
                ),
                new MilestoneTemplate(
                        "Không còn nicotine trong cơ thể",
                        "Nicotine và các sản phẩm phụ được đào thải khỏi cơ thể sau khoảng 1 ngày.",
                        Duration.ofDays(1)
                ),
                new MilestoneTemplate(
                        "Cải thiện hô hấp",
                        "Phổi bắt đầu hồi phục, lượng đờm giảm, thở dễ dàng hơn sau 2 ngày.",
                        Duration.ofDays(2)
                ),
                new MilestoneTemplate(
                        "Cảm nhận vị và mùi tốt hơn",
                        "Sau 3 ngày, vị giác và khứu giác cải thiện rõ rệt do đầu dây thần kinh hồi phục.",
                        Duration.ofDays(3)
                ),
                new MilestoneTemplate(
                        "Ít ho hơn và dễ thở",
                        "Sau 1–2 tuần, chức năng phổi bắt đầu cải thiện, bạn sẽ cảm thấy ít ho hơn và hít thở dễ dàng hơn.",
                        Duration.ofDays(14)
                ),
                new MilestoneTemplate(
                        "Tuần hoàn máu cải thiện",
                        "Sau 1 tháng, tuần hoàn máu được cải thiện rõ rệt, việc vận động trở nên dễ dàng hơn.",
                        Duration.ofDays(30)
                ),
                new MilestoneTemplate(
                        "Chức năng phổi tăng 30%",
                        "Từ 2 đến 3 tháng sau, dung tích phổi tăng lên đến 30%, giảm mệt mỏi và khó thở.",
                        Duration.ofDays(90)
                ),
                new MilestoneTemplate(
                        "Da sáng hơn, ít nếp nhăn",
                        "Sau 3–6 tháng, lưu lượng máu được cải thiện giúp da sáng và trẻ trung hơn.",
                        Duration.ofDays(180)
                ),
                new MilestoneTemplate(
                        "Giảm nguy cơ bệnh tim 50%",
                        "Sau 1 năm không hút thuốc, nguy cơ mắc bệnh tim mạch giảm một nửa so với người hút thuốc.",
                        Duration.ofDays(365)
                ),
                new MilestoneTemplate(
                        "Giảm nguy cơ đột quỵ như người không hút",
                        "Sau 5 năm, nguy cơ đột quỵ tương đương với người chưa từng hút thuốc.",
                        Duration.ofDays(365 * 5)
                ),
                new MilestoneTemplate(
                        "Giảm nguy cơ ung thư phổi",
                        "Sau 10 năm, nguy cơ ung thư phổi giảm một nửa so với người hút thuốc.",
                        Duration.ofDays(365 * 10)
                ),
                new MilestoneTemplate(
                        "Cơ thể như người chưa từng hút thuốc",
                        "Sau 15 năm, nguy cơ mắc bệnh tim, phổi gần như trở về mức bình thường.",
                        Duration.ofDays(365 * 15)
                )
        );
        return templates;
    }
}
