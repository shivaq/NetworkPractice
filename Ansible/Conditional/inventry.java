web1 ansible_host=server1.company.com ansible_connection=ssh ansible_user=root ansible_ssh_pass=Password123!
db ansible_host=db.company.com ansible_connection=winrm ansible_user=administrator ansible_password=Password123!
web2 ansible_host=server2.company.com ansible_connection=ssh ansible_user=root ansible_ssh_pass=Password123!

[all_servers]
web1
db
web2
