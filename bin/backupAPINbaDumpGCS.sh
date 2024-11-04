#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

source "$SCRIPT_DIR/../.env"

# These should come from the .env
MONGO_HOST="$NBA_API_MONGO_HOST"
MONGO_PORT="$NBA_API_MONGO_PORT"
MONGO_AUTH_DB="$NBA_API_MONGO_AUTH_DB"
MONGO_DB="$NBA_API_MONGO_DB"
MONGO_USER="$NBA_API_MONGO_USER"
MONGO_PASSWORD="$NBA_API_MONGO_PASSWORD"

DUMP_DIR="$SCRIPT_DIR/../dump"
TIMESTAMP=$(date +"%Y%m%d%H%M")
DUMP_PATH="$DUMP_DIR/$MONGO_DB-$TIMESTAMP"

# Create dump directory if it doesn't exist
mkdir -p "$DUMP_DIR"

echo "Connecting to MongoDB at $MONGO_HOST:$MONGO_PORT"
echo "Using database: $MONGO_DB"

echo "Backing up MongoDB database: $MONGO_DB"
mongodump --host "$MONGO_HOST" --port "$MONGO_PORT" --username "$MONGO_USER" --password "$MONGO_PASSWORD" --authenticationDatabase "$MONGO_AUTH_DB" --db "$MONGO_DB" --out "$DUMP_PATH"

if [ $? -eq 0 ]; then
    echo "MongoDB dump successful: $DUMP_PATH"

    GCS_BUCKET="gs://$GOOGLE_CLOUD_STORAGE_BUCKET_MAIN/dump/$MONGO_DB-$TIMESTAMP"
    echo "Uploading backup to GCS bucket: $GCS_BUCKET"
    gsutil -m cp -r "$DUMP_PATH" "$GCS_BUCKET"

    if [ $? -eq 0 ]; then
        echo "Upload successful!"

        echo "Removing dump directory: $DUMP_PATH"
        rm -rf "$DUMP_PATH"

        if [ $? -eq 0 ]; then
            echo "Dump directory removed successfully."
        else
            echo "Failed to remove dump directory."
        fi
    else
        echo "Error during upload to GCS."
    fi
else
    echo "Error during MongoDB dump."
fi