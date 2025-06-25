INSERT INTO achievement_templates (title, description, category, custom_logic_key, visible) VALUES
-- 1. MONEY SAVED -------------------------------------------------------------
(N'Tiết kiệm 100 000 ₫',  N'Bạn đã tránh tiêu 100 000 đồng nhờ không mua thuốc lá.',        N'MONEY_SAVED', 'MONEY_SAVED_100K', 1),
(N'Tiết kiệm 500 000 ₫',  N'Bạn đã tránh tiêu 500 000 đồng kể từ khi bỏ thuốc.',             N'MONEY_SAVED', 'MONEY_SAVED_500K', 1),
(N'Tiết kiệm 1 000 000 ₫',N'Bạn đã tránh tiêu 1 000 000 đồng kể từ khi bỏ thuốc.',           N'MONEY_SAVED', 'MONEY_SAVED_1M',   1),
(N'Tiết kiệm 5 000 000 ₫',N'Bạn đã tránh tiêu 5 000 000 đồng kể từ khi bỏ thuốc.',           N'MONEY_SAVED', 'MONEY_SAVED_5M',   1),

-- 2. DAYS QUIT SMOKING -------------------------------------------------------
(N'14 ngày bỏ thuốc',     N'Bạn đã vượt mốc 14 ngày không hút thuốc – cố lên!',              N'DAYS_QUIT_SMOKING', 'DAYS_QUIT_SMOKING_14', 1),
(N'30 ngày bỏ thuốc',     N'Bạn đã bỏ thuốc tròn 30 ngày – tiếp tục duy trì nhé!',           N'DAYS_QUIT_SMOKING', 'DAYS_QUIT_SMOKING_30', 1),

-- 3. STREAK NO SMOKE ---------------------------------------------------------
(N'Chuỗi 1 ngày sạch khói',  N'Bạn vừa hoàn thành ngày đầu tiên không hút thuốc.',          N'STREAK_NO_SMOKE', 'STREAK_NO_SMOKE_1',  1),
(N'Chuỗi 7 ngày sạch khói',  N'Bạn đã không hút thuốc 7 ngày liên tục – tuyệt vời!',        N'STREAK_NO_SMOKE', 'STREAK_NO_SMOKE_7',  1),
(N'Chuỗi 30 ngày sạch khói', N'Bạn đã duy trì 30 ngày liên tục không hút thuốc!',           N'STREAK_NO_SMOKE', 'STREAK_NO_SMOKE_30', 1),

-- 4. FIRST DAY ---------------------------------------------------------------
(N'Ngày đầu thay đổi',    N'Chúc mừng! Bạn đã khởi đầu hành trình bỏ thuốc.',               N'FIRST_DAY',   'FIRST_DAY', 1),

-- 5. NUMBER OF DIARY ---------------------------------------------------------
(N'Nhật ký đầu tiên',     N'Bạn đã ghi lại cảm xúc đầu tiên trong nhật ký bỏ thuốc.',       N'NUMBER_OF_DIARY', 'NUMBER_OF_DIARY_1',  1),
(N'7 lượt nhập nhật ký',  N'Bạn đã viết 7 lần nhật ký – tiếp tục sẻ chia trải nghiệm!',     N'NUMBER_OF_DIARY', 'NUMBER_OF_DIARY_7',  1),
(N'30 lượt nhập nhật ký', N'Bạn đã duy trì 30 lần ghi nhật ký – nỗ lực tuyệt vời!',         N'NUMBER_OF_DIARY', 'NUMBER_OF_DIARY_30', 1),

-- 6. NUMBER OF TASK_COMPLETE ---------------------------------------------------------
(N'Hoàn thành 5 nhiệm vụ',     N'Bạn đã hoàn thành 5 nhiệm vụ – khởi đầu tốt đẹp!',                 N'NUMBER_OF_TASK_COMPLETE', 'NUMBER_OF_TASK_COMPLETE_5',   1),
(N'Hoàn thành 10 nhiệm vụ',    N'Bạn đã hoàn thành 10 nhiệm vụ – tiếp tục phát huy!',               N'NUMBER_OF_TASK_COMPLETE', 'NUMBER_OF_TASK_COMPLETE_10',  1),
(N'Hoàn thành 20 nhiệm vụ',    N'Bạn đã hoàn thành 20 nhiệm vụ – bạn đang đi đúng hướng!',         N'NUMBER_OF_TASK_COMPLETE', 'NUMBER_OF_TASK_COMPLETE_20',  1),
(N'Hoàn thành 30 nhiệm vụ',    N'Bạn đã hoàn thành 30 nhiệm vụ – sự kiên trì đang mang lại kết quả!', N'NUMBER_OF_TASK_COMPLETE', 'NUMBER_OF_TASK_COMPLETE_30',  1),
(N'Hoàn thành 50 nhiệm vụ',    N'Bạn đã hoàn thành 50 nhiệm vụ – một thành tích đáng tự hào!',      N'NUMBER_OF_TASK_COMPLETE', 'NUMBER_OF_TASK_COMPLETE_50',  1),
(N'Hoàn thành 100 nhiệm vụ',   N'Bạn đã hoàn thành 100 nhiệm vụ – bạn là người cực kỳ kiên định!', N'NUMBER_OF_TASK_COMPLETE', 'NUMBER_OF_TASK_COMPLETE_100', 1);

INSERT INTO member_plans(plan_name, description, price, features, created_at, updated_at)
VALUES
    (N'Gói Cơ bản', N'Cho phép đặt lịch liên hệ trực tiếp với chuyên gia', 199000, N'- Hỗ trợ đặt lịch chuyên gia', GETDATE(), GETDATE());



