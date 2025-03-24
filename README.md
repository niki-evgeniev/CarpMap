# CarpMap

CarpMap is a water reservoir management application designed for anglers and fishing enthusiasts.  
The project is implemented using **Spring Boot** and **Thymeleaf**, offering functionalities such as reservoir search,
detailed reservoir information, fish type, blog posts, and more.

### **Live Demo**

Visit the home page: [Carpmap.bg ](https://carpmap.bg)

---

## **Features**

### **Reservoir Management**

- Add new reservoirs
- View all reservoirs in Bulgaria
- Detailed reservoir information
- Search reservoirs by name or type
- Edit reservoirs:
    - Change name, iframe, descriptions, and more
    - Update or delete pictures (from Cloudinary)
    - Remove reservoirs

### **Level and volume**
 - Check the status of reservoirs in Bulgaria

### **Fish Type Management**

- Add new fish types
- View all fish types in Bulgaria
- Detailed fish information

### **User Management**

- User registration and profile updates
- Change password
- Update user details (email, Facebook, Twitter, etc.)

### **Blog and Contact**

- Create and manage blog posts
- Contact form for inquiries

### **Admin Panel**

- Manage users and roles (USER, MODERATOR, ADMIN)
- IP management for access control:
    - View visitor statistics (daily, monthly, etc.)
    - Ban/unban IPs
- Edit user details (email, social links, etc.)

### **Mail System**

- Receive and delete messages
- View message details

### **Scheduler**

- Check for duplicate IP addresses
- Add new pages to `sitemap.xml`

### **Server Monitoring**

- View system information:
    - Memory usage (used, free, total)
    - Application version
    - CPU information and load
    - Uptime
    - Registered reservoir and user counters
    - SSD usage and free space
    - Network details

### **SEO & Analytics**

- Google Analytics – tracking traffic and user behavior
- Google Search Console – indexing pages, detecting SEO errors, and improving visibility
      
---

## **Technologies Used**

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **Thymeleaf**
- **MySQL**
- **HTML, CSS, Bootstrap, JavaScript**
- **Actuator**
- **Oshi** (for system monitoring)
- **Responsive Design**
- **ReservoirAPI (for real-time water level and volume data) -> [ReservoirAPI](https://github.com/niki-evgeniev/reservoirAPI)**

---

## **Future Improvements**

- Enhance the UI/UX design
- Add advanced search filters for reservoirs
- Introduce user subscription plans for premium features

---

## **Contributing**

We welcome contributions! Feel free to fork the repository and submit pull requests.

---

## **Contact**

For any questions, feel free to reach out via the [contact form](https://carpmap.bg/contact).  
