#!/bin/bash

awslocal sqs create-queue --queue-name DiscordNotificationDeadQueue.fifo --attributes FifoQueue=true
awslocal sqs create-queue --queue-name DiscordNotificationQueue.fifo --attributes FifoQueue=true,RedrivePolicy="\"{\\\"deadLetterTargetArn\\\":\\\"arn:aws:sqs:us-east-1:000000000000:DiscordNotificationDeadQueue.fifo\\\",\\\"maxReceiveCount\\\":\\\"5\\\"}\""
