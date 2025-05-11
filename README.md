# Log Parser CLI Application
## Description

A robust Java-based command-line application that analyzes a `.txt` file containing mixed log entries. It intelligently classifies and processes APM, Application, and Request logs, generating structured JSON outputs for downstream analysis or monitoring pipelines.


## Project Objective

The purpose of this tool is to automate the interpretation of a heterogeneous log file where each line may belong to a different category:

- **APM Logs**: System performance metrics like CPU, memory, disk, etc.
- **Application Logs**: Message logs with levels like INFO, DEBUG, ERROR.
- **Request Logs**: Details about HTTP calls made to backend APIs.

For each type, the application extracts relevant information and computes tailored aggregations, ultimately saving the results into corresponding `.json` files.



## Sample Input Log File

      timestamp=2024-02-24T16:22:15Z metric=cpu_usage_percent host=webserver1 value=72
      timestamp=2024-02-24T16:22:20Z level=INFO message="Scheduled maintenance starting" host=webserver1
      timestamp=2024-02-24T16:22:25Z request_method=POST request_url="/api/update" response_status=202 response_time_ms=200 host=webserver1
      timestamp=2024-02-24T16:22:30Z metric=memory_usage_percent host=webserver1 value=85
      timestamp=2024-02-24T16:22:35Z level=ERROR message="Update process failed" error_code=5012 host=webserver1
      timestamp=2024-02-24T16:22:40Z request_method=GET request_url="/api/status" response_status=200 response_time_ms=100 host=webserver1
      timestamp=2024-02-24T16:22:45Z metric=disk_usage_percent mountpoint=/ host=webserver1 value=68
      timestamp=2024-02-24T16:22:50Z level=DEBUG message="Retrying update process" attempt=1 host=webserver1
      timestamp=2024-02-24T16:22:55Z request_method=POST request_url="/api/retry" response_status=503 response_time_ms=250 host=webserver1
      timestamp=2024-02-24T16:23:00Z metric=network_bytes_in host=webserver1 interface=eth0 value=543210
      timestamp=2024-02-24T16:23:05Z level=INFO message="Update process completed successfully" host=webserver1
      timestamp=2024-02-24T16:23:10Z request_method=GET request_url="/home" response_status=404 response_time_ms=25 host=webserver1
      timestamp=2024-02-24T16:23:15Z metric=network_bytes_out host=webserver1 interface=eth0 value=123456
      timestamp=2024-02-24T16:23:20Z level=WARNING message="High memory usage detected" host=webserver1
      timestamp=2024-02-24T16:23:25Z request_method=GET request_url="/api/status" response_status=200 response_time_ms=150 host=webserver1
      timestamp=2024-02-24T16:23:30Z metric=cpu_usage_percent host=webserver2 value=65
      timestamp=2024-02-24T16:23:35Z level=ERROR message="Database connection timeout" host=webserver2
      timestamp=2024-02-24T16:23:40Z request_method=POST request_url="/api/status" response_status=500 response_time_ms=300 host=webserver2
      timestamp=2024-02-24T16:23:45Z metric=memory_usage_percent host=webserver2 value=90
      timestamp=2024-02-24T16:23:50Z level=INFO message="New user registered" user_id=789 host=webserver2
      timestamp=2024-02-24T16:23:55Z request_method=GET request_url="/api/status" response_status=200 response_time_ms=180 host=webserver2

---

## Example Outputs


### **APM Aggregation (apm.json)**

      {
        "cpu_usage_percent": {
          "minimum": 65,
          "median": 72,
          "average": 68.5,
          "max": 72
        }
      }


### **Application Log Summary (application.json)**

      {
        "INFO": 2,
        "ERROR": 1
      }


### **Request Stats (request.json)**

      {
        "/api/status": {
          "response_times": {
            "min": 100,
            "50_percentile": 150,
            "90_percentile": 200,
            "max": 300
          },
          "status_codes": {
            "2XX": 2,
            "5XX": 1
          }
        }
      }


# How to Execute

**Prerequisites**

* Java 17+

* Maven 3.6+


### Run Instructions

**1. Build the application:**

      mvn clean compile

**2.Execute via CLI:**

      mvn exec:java -Dexec.args="--file input.txt"

**3. Expected Outputs:**

* apm.json

* application.json

* request.json

These files are generated in your project directory.

### Testing the System

Unit tests are available to validate:

* Metric-based aggregation (APM)

* Severity count (Application logs)

* Percentile and status code analysis (Request logs)

**To run all tests:**

      mvn test

# Architecture and Design Patterns

| Pattern      | Description                                                                       |
| ------------ | --------------------------------------------------------------------------------- |
| **Strategy** | Provides flexibility for parsing each log type independently via polymorphism.    |
| **Factory**  | Centralizes parser selection based on log line content.                           |
| **Utility**  | Static helper classes simplify operations like JSON writing and statistical math. |

A full design document is available in log_aggregator_design.pdf, including class diagram and rationale.
