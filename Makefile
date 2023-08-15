init-devcontainer:
	docker-compose -f ./devcontainer/docker-compose.yml up -d

clean-devcontainer:
	docker-compose -f ./devcontainer/docker-compose.yml down
	rm -rf ./devcontainer/mysql
	rm -rf ./devcontainer/localstack