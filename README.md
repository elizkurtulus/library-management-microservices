# 📚 Library Management Microservices

Modern, production-ready bir kütüphane yönetim sistemi mikroservis mimarisi. Spring Boot, Spring Cloud ve Netflix Eureka kullanılarak geliştirilmiştir.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)

---

## İçindekiler

- [Özellikler](#-özellikler)
- [Mimari](#-mimari)
- [Teknoloji Stack](#-teknoloji-stack)
- [Proje Yapısı](#-proje-yapısı)
- [Kurulum](#-kurulum)
- [Çalıştırma](#-çalıştırma)
- [API Dokümantasyonu](#-api-dokümantasyonu)
- [Test](#-test)
- [Docker](#-docker)
- [Katkıda Bulunma](#-katkıda-bulunma)
- [Lisans](#-lisans)

---

## ✨ Özellikler

### 🏗️ Mikroservis Mimarisi

- **9 bağımsız mikroservis** - Her servis kendi veritabanına sahip
- **Service Discovery** - Netflix Eureka ile servis keşfi
- **API Gateway** - Spring Cloud Gateway ile merkezi routing
- **Config Server** - Merkezi yapılandırma yönetimi
- **Load Balancing** - Eureka ile otomatik load balancing

### 📦 Business Servisler

- **Book Service** - Kitap yönetimi (CRUD, arama, stok yönetimi)
- **Category Service** - Kategori yönetimi
- **Member Service** - Üye yönetimi (kayıt, güncelleme, durum takibi)
- **Loan Service** - Ödünç verme işlemleri (ödünç alma, iade, geç iade takibi)
- **Fine Service** - Ceza yönetimi (geç iade cezaları, ödeme takibi)
- **Reservation Service** - Rezervasyon yönetimi (bekleme listesi, rezervasyon durumu)

### 🔧 Teknik Özellikler

- **RESTful API** - Standart REST endpoint'leri
- **Bean Validation** - Request/Response validation
- **Global Exception Handling** - Merkezi hata yönetimi
- **DTO Pattern** - Entity'lerin direkt exposure'ını önleme
- **Service Layer** - Business logic ayrımı
- **Repository Pattern** - JPA ile veri erişimi
- **MapStruct** - Otomatik DTO mapping (Loan & Fine service)
- **Feign Client** - Servisler arası iletişim
- **Actuator** - Health check ve monitoring
- **Docker Support** - Containerization hazır

---

## 🏛️ Mimari

```
┌─────────────────────────────────────────────────────────────┐
│                    API Gateway (8888)                        │
│              Spring Cloud Gateway                            │
└────────────────────┬────────────────────────────────────────┘
                     │
        ┌────────────┴────────────┐
        │                         │
┌───────▼────────┐      ┌─────────▼──────────┐
│ Discovery      │      │ Config Server      │
│ Server (8761)  │      │ (8787)             │
│ Eureka         │      │                    │
└───────┬────────┘      └────────────────────┘
        │
        ├─── Book Service (8081) ────┐
        ├─── Category Service (8082)  │
        ├─── Fine Service (8083)     │
        ├─── Loan Service (8084)     │─── PostgreSQL Databases
        ├─── Member Service (8085)   │    (5432-5437)
        └─── Reservation Service (8086) ──┘
```

### Servis Bağımlılıkları

- **Loan Service** → Book Service (Feign Client)
- **Book Service** → Category Service (Feign Client)
- **Fine Service** → Member Service (business rules)
- **Reservation Service** → Book Service, Member Service

---

## 🛠️ Teknoloji Stack

### Backend Framework

- **Spring Boot** 3.5.6
- **Spring Cloud** 2025.0.0
- **Java** 21

### Service Discovery & Configuration

- **Netflix Eureka** - Service Discovery
- **Spring Cloud Config Server** - Centralized Configuration

### API & Communication

- **Spring Cloud Gateway** - API Gateway
- **OpenFeign** - Service-to-Service Communication
- **RESTful API** - HTTP/JSON

### Database

- **PostgreSQL** 16
- **Spring Data JPA** - ORM
- **Hibernate** - JPA Implementation

### Mapping & Validation

- **MapStruct** 1.5.5 - DTO Mapping (Loan & Fine service)
- **Bean Validation** (Jakarta Validation API + Hibernate Validator)

### Infrastructure

- **Docker** & **Docker Compose** - Containerization
- **Maven** - Build Tool
- **Spring Boot Actuator** - Monitoring & Health Checks

### Messaging (Opsiyonel)

- **Apache Kafka** 3.8.0 - Event Streaming

---

## 📁 Proje Yapısı

```
library-management-microservices/
│
├── config-server/              # Spring Cloud Config Server
│   ├── src/main/java/.../ConfigServerApplication.java
│   └── src/main/resources/application.yml
│
├── discovery-server/            # Netflix Eureka Discovery Server
│   ├── src/main/java/.../DiscoveryServerApplication.java
│   └── src/main/resources/application.yml
│
├── gateway-server/              # Spring Cloud Gateway
│   ├── src/main/java/.../GatewayServerApplication.java
│   └── src/main/resources/application.yml
│
├── book-service/               # Kitap Yönetimi
│   ├── src/main/java/.../
│   │   ├── entity/Book.java
│   │   ├── repository/BookRepository.java
│   │   ├── service/BookService.java
│   │   ├── controller/BookController.java
│   │   └── dto/BookRequest.java, BookResponse.java
│   └── src/main/resources/application.yml
│
├── category-service/           # Kategori Yönetimi
│   └── ...
│
├── member-service/             # Üye Yönetimi
│   └── ...
│
├── loan-service/               # Ödünç Verme İşlemleri
│   ├── src/main/java/.../
│   │   ├── entity/Loan.java
│   │   ├── mapper/LoanMapper.java (MapStruct)
│   │   ├── rules/LoanBusinessRules.java
│   │   └── ...
│   └── ...
│
├── fine-service/               # Ceza Yönetimi
│   ├── src/main/java/.../
│   │   ├── mapper/FineMapper.java (MapStruct)
│   │   └── ...
│   └── ...
│
├── reservation-service/        # Rezervasyon Yönetimi
│   └── ...
│
├── configurations/             # Config Server Repository
│   ├── book-service/
│   ├── category-service/
│   ├── member-service/
│   └── ...
│
└── docker-compose.yml          # Docker Compose Configuration
```

---

## 🚀 Kurulum

### Gereksinimler

- **Java** 21+
- **Maven** 3.9+
- **Docker** & **Docker Compose** (opsiyonel, database'ler için)
- **PostgreSQL** 16 (veya Docker'da çalışan)
- **Git** (Config Server için)

### Adım 1: Repository'yi Klonlayın

```bash
git clone https://github.com/elizkurtulus/library-management-microservices.git
cd library-management-microservices
```

### Adım 2: Database'leri Başlatın

**Docker ile (Önerilen):**

```bash
docker-compose up -d booksv_db categorysv_db finesv_db loansv_db membersv_db reservationsv_db
```

**Manuel Kurulum:**

Her servis için ayrı PostgreSQL database oluşturun:

- `book_service` (port 5432)
- `category_service` (port 5433)
- `fine_service` (port 5434)
- `loan_service` (port 5435)
- `member_service` (port 5436)
- `reservation_service` (port 5437)

---

## ▶️ Çalıştırma

### Yöntem 1: Yerel Çalıştırma (IDE/Terminal)

#### ⚠️ ÖNEMLİ: MapStruct için Maven Compile

**Loan Service** ve **Fine Service** MapStruct kullanıyor. İlk çalıştırmadan önce:

```bash
cd loan-service
mvn clean compile

cd ../fine-service
mvn clean compile
```

#### Servisleri Başlatma Sırası

1. **Config Server (Opsiyonel)**

   ```bash
   cd config-server
   mvn spring-boot:run
   ```

   ✅ http://localhost:8787

2. **Discovery Server (ZORUNLU)**

   ```bash
   cd discovery-server
   mvn spring-boot:run
   ```

   ✅ Eureka Dashboard: http://localhost:8761

3. **Business Services** (Sıra önemli değil)

   ```bash
   # Book Service
   cd book-service && mvn spring-boot:run

   # Category Service
   cd category-service && mvn spring-boot:run

   # Member Service
   cd member-service && mvn spring-boot:run

   # Loan Service (MapStruct compile gerekli)
   cd loan-service && mvn clean compile && mvn spring-boot:run

   # Fine Service (MapStruct compile gerekli)
   cd fine-service && mvn clean compile && mvn spring-boot:run

   # Reservation Service
   cd reservation-service && mvn spring-boot:run
   ```

4. **Gateway Server (Opsiyonel)**
   ```bash
   cd gateway-server
   mvn spring-boot:run
   ```
   ✅ http://localhost:8888

### Yöntem 2: Docker Compose

```bash
# Config Server ve Discovery Server'ı başlat
docker-compose up --build config-server discovery-server

# Database'leri başlat
docker-compose up -d booksv_db categorysv_db finesv_db loansv_db membersv_db reservationsv_db

# Business servisleri yerel olarak çalıştırın (veya docker-compose.yml'de aktif hale getirin)
```

---

## 📡 API Dokümantasyonu

### Base URLs

| Servis              | Port | Base URL                                      |
| ------------------- | ---- | --------------------------------------------- |
| Book Service        | 8081 | `http://localhost:8081/api/v1/books`          |
| Category Service    | 8082 | `http://localhost:8082/api/v1/categories`     |
| Fine Service        | 8083 | `http://localhost:8083/api/v1/fines`          |
| Loan Service        | 8084 | `http://localhost:8084/api/v1/loans`          |
| Member Service      | 8085 | `http://localhost:8085/api/v1/members`        |
| Reservation Service | 8086 | `http://localhost:8086/api/v1/reservations`   |
| Gateway Server      | 8888 | `http://localhost:8888/api/v1/{service-name}` |

### Örnek API İstekleri

#### Member Service - Yeni Üye Oluştur

```bash
POST http://localhost:8085/api/v1/members
Content-Type: application/json

{
  "memberNumber": "M001",
  "firstName": "Ahmet",
  "lastName": "Yılmaz",
  "email": "ahmet@example.com",
  "phoneNumber": "5551234567",
  "status": "ACTIVE"
}
```

#### Book Service - Yeni Kitap Oluştur

```bash
POST http://localhost:8081/api/v1/books
Content-Type: application/json

{
  "title": "Spring Boot ile Mikroservis Mimarisi",
  "author": "Ahmet Yılmaz",
  "isbn": "978-1234567890",
  "categoryId": 1,
  "stockQuantity": 10,
  "availableQuantity": 10
}
```

#### Loan Service - Ödünç Verme Oluştur

```bash
POST http://localhost:8084/api/v1/loans
Content-Type: application/json

{
  "memberId": "550e8400-e29b-41d4-a716-446655440000",
  "bookId": "550e8400-e29b-41d4-a716-446655440001",
  "dueDate": "2025-02-22T10:00:00.000Z",
  "loanDate": "2025-01-22T10:00:00.000Z"
}
```

**Not:** Detaylı API dokümantasyonu için `POSTMAN_API_ENDPOINTS.md` dosyasına bakın (eğer mevcutsa).

---

## 🧪 Test

### Test Coverage

- **Member Service:** Kapsamlı unit ve integration testler mevcut
- **Loan Service:** Temel service ve controller testleri mevcut
- **Diğer Servisler:** Context load testleri mevcut

### Test Çalıştırma

```bash
# Tüm servisler için
mvn test

# Belirli bir servis için
cd member-service
mvn test
```

---

## 🐳 Docker

### Docker Compose Servisleri

```yaml
# Aktif Servisler
- config-server (8787)
- discovery-server (8761)
- PostgreSQL databases (5432-5437)
- Kafka (9092, 9094)
- Kafka UI (8080)

# Yorum Satırında (Aktif Hale Getirilebilir)
- gateway-server (8888)
- book-service (8081)
- category-service (8082)
- fine-service (8083)
- loan-service (8084)
- member-service (8085)
- reservation-service (8086)
```

### Docker Komutları

```bash
# Tüm servisleri başlat
docker-compose up --build

# Arka planda çalıştır
docker-compose up -d

# Belirli servisleri başlat
docker-compose up config-server discovery-server

# Logları görüntüle
docker-compose logs -f discovery-server

# Servisleri durdur
docker-compose down

# Volume'ları da sil
docker-compose down -v
```

---

## 🔍 Servis Detayları

### Port Numaraları

| Servis              | Port | Database Port |
| ------------------- | ---- | ------------- |
| config-server       | 8787 | -             |
| discovery-server    | 8761 | -             |
| gateway-server      | 8888 | -             |
| book-service        | 8081 | 5432          |
| category-service    | 8082 | 5433          |
| fine-service        | 8083 | 5434          |
| loan-service        | 8084 | 5435          |
| member-service      | 8085 | 5436          |
| reservation-service | 8086 | 5437          |

### Health Check Endpoints

Tüm servislerde Actuator health endpoint'leri mevcut:

- `http://localhost:8081/actuator/health` (book-service)
- `http://localhost:8082/actuator/health` (category-service)
- `http://localhost:8083/actuator/health` (fine-service)
- `http://localhost:8084/actuator/health` (loan-service)
- `http://localhost:8085/actuator/health` (member-service)
- `http://localhost:8086/actuator/health` (reservation-service)

### Eureka Dashboard

- **URL:** http://localhost:8761
- Tüm kayıtlı servisleri görüntüleyin
- Servis durumlarını kontrol edin

---

## 🔧 Yapılandırma

### Config Server

Config Server, Git repository'den yapılandırmaları yükler:

- Repository: `https://github.com/elizkurtulus/library-management-microservices.git`
- Branch: `master`
- Path: `configurations/`

### Profiller

- **dev** - Yerel geliştirme (default)
- **docker** - Docker container'lar için

### Database Yapılandırması

Her servis kendi PostgreSQL database'ine sahiptir. Yapılandırma `application.yml` dosyalarında mevcuttur.

---

## 🐛 Sorun Giderme

### Problem: Loan/Fine Service - "No qualifying bean of type 'LoanMapper'/'FineMapper'"

**Çözüm:**

```bash
cd loan-service
mvn clean compile

cd fine-service
mvn clean compile
```

IDE'de annotation processing'i etkinleştirin (IntelliJ: Settings → Compiler → Annotation Processors).

### Problem: Config Server bağlantı hatası

**Çözüm:** Config Server opsiyoneldir. Yerel çalıştırmada çalışmıyorsa, servisler local `application.yml` kullanır (hata vermez).

### Problem: Database bağlantı hatası

**Çözüm:**

1. PostgreSQL container'larının çalıştığını kontrol edin: `docker-compose ps`
2. Database şifrelerinin `application.yml` ile eşleştiğinden emin olun
3. Port çakışması olup olmadığını kontrol edin

### Problem: Servisler Eureka'ya kayıt olmuyor

**Çözüm:**

1. Discovery Server'ın çalıştığından emin olun: http://localhost:8761
2. `application.yml`'de Eureka client yapılandırmasını kontrol edin
3. Network bağlantısını kontrol edin

---

## 📊 Mimari Kararlar

### Design Patterns

- **Repository Pattern** - Veri erişim katmanı
- **Service Layer Pattern** - Business logic ayrımı
- **DTO Pattern** - Entity'lerin direkt exposure'ını önleme
- **Global Exception Handler** - Merkezi hata yönetimi
- **Builder Pattern** - MapStruct ile otomatik mapping

### Best Practices

- ✅ Her servis kendi veritabanına sahip (Database per Service)
- ✅ RESTful API standartlarına uyum
- ✅ Bean Validation ile input validation
- ✅ HTTP status kodları doğru kullanılıyor (201 Created, 204 No Content, vb.)
- ✅ Consistent error response format
- ✅ Actuator endpoints ile monitoring
- ✅ Docker support ile containerization

---

## 👥 Katkıda Bulunma

1. Fork edin
2. Feature branch oluşturun (`git checkout -b feature/AmazingFeature`)
3. Commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Push edin (`git push origin feature/AmazingFeature`)
5. Pull Request açın

---

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

---

## 🙏 Teşekkürler

- Spring Boot ve Spring Cloud ekibine
- Netflix Eureka ekibine
- Tüm açık kaynak topluluğuna
- Geleceği Yazanlar Ekibine
- Java eğitmenimiz Halit Enes Kalaycı
- Turkcell ailesine teşekkürler

---

**⭐ Bu projeyi beğendiyseniz, yıldız vermeyi unutmayın!**
