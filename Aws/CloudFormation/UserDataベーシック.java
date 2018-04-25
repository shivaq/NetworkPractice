▼ EC2-user data
-------------------------------------------------
EC2 作成時の、ブートストラップの bash のこと
・スクリプトが長くなると、管理しづらい
・EC2 を terminate せずに更新したい場合に困る
・可読性が低い
・スクリプトの完了成否がわからない
-------------------------------------------------
UserData:
  "Fn::Base64":
    !Sub |
      #!/bin/bash
      yum install httpd php mysql php-mysql -y
      yum update -y
      chkconfig httpd on
      service httpd start
      cd /var/www/html
      wget https://wordpress.org/latest.tar.gz
      tar -zxvf latest.tar.gz --strip 1
      rm latest.tar.gz
      cp wp-config-sample.php wp-config.php
      sed -i 's/database_name_here/${DatabaseName}/g' wp-config.php
      sed -i 's/localhost/${DB.Endpoint.Address}/g' wp-config.php
      sed -i 's/username_here/${DatabaseUser}/g' wp-config.php
      sed -i 's/password_here/${DatabasePassword}/g' wp-config.php
-------------------------------------------------
