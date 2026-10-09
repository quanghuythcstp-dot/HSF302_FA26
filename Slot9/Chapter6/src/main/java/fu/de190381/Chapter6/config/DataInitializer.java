package fu.de190381.Chapter6.config;

import fu.de190381.Chapter6.entity.Major;
import fu.de190381.Chapter6.entity.Student;
import fu.de190381.Chapter6.repository.MajorRepository;
import fu.de190381.Chapter6.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    public DataInitializer(StudentRepository studentRepository,
                           MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.majorRepository   = majorRepository;
    }

    @Override
    public void run(String... args) {
        // 1. Seed bảng majors
        if (majorRepository.count() == 0) {
            majorRepository.saveAll(List.of(
                    new Major("CNTT", "Công nghệ thông tin"),
                    new Major("KTPM", "Kỹ thuật phần mềm"),
                    new Major("HTTT", "Hệ thống thông tin"),
                    new Major("ATTT", "An toàn thông tin"),
                    new Major("MMT",  "Mạng máy tính")
            ));
            log.info("Đã seed {} majors", majorRepository.count());
        }

        // 2. Seed bảng students
        if (studentRepository.count() > 0) {
            log.info("Bảng students đã có dữ liệu → bỏ qua seed");
            return;
        }

        Map<String, Major> majorMap = majorRepository.findAll()
                .stream().collect(Collectors.toMap(Major::getCode, m -> m));

        List<Student> students = List.of(
                new Student("Nguyễn Văn An",  "an@fpt.edu.vn",    20, "CNTT", 3.5),
                new Student("Trần Thị Bình",  "binh@fpt.edu.vn",  21, "KTPM", 3.2),
                new Student("Lê Minh Cường",  "cuong@fpt.edu.vn", 19, "ATTT", 3.8),
                new Student("Phạm Thị Dung",  "dung@fpt.edu.vn",  22, "HTTT", 2.9)
        );
        // Gán majorEntity cho từng student
        students.forEach(s -> s.setMajorEntity(majorMap.get(s.getMajor())));

        studentRepository.saveAll(students);
        log.info("Đã seed {} sinh viên vào bảng students", studentRepository.count());
    }
}
