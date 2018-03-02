docker is configured to use the default machine with IP 192.168.99.100
For help getting started, check out the docs at https://docs.docker.com

Start interactive shell
-------------------------------------------------
１．
Once you install docker-toolbox, you'll need to use docker-machine to get a docker host vm up and running on virtualbox.
docker-machine create dev -d virtualbox
２．
With docker-machine up, you can add the connection details to your shell:
eval "$(docker-machine env dev)"
３．
Use an ssh keypair generator to create a keypair named ansible:
ssh-keygen -t rsa -f ansible
４．
Download the setup files from the attachments on this lecture, unpack the archive to a directory on your machine.
Copy the keypair you created in step 3 into the env/ subdirectory.

５．
// ここで詰まった。 Git からやると、文字化けのためか下記エラー
// Windows named pipe error:
// Docker のターミナルからだとちゃんとできた。
From the unpacked directory enter:
docker-compose build && docker-compose up


In another terminal session (or using an ssh client),
connect to the hosts using the ansible key you created,
and IP and Port mapping for the instance.

Use docker-machine ip dev to print the IP for your docker host;
the port mappings are found in the docker-compose.yml.
You can also use the provided config file, by moving it to
~/.ssh/config on your local machine and updating the IP to match your docker host.
Then you can simply ssh control to access the control host.
