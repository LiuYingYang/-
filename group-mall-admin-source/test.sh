 if [ "$GIT_BRANCH" == "dev" ]; then 
    npm run build:dev
 fi

 if [ "$GIT_BRANCH" == "test" ]; then 
    npm run build:test 
 fi

 if [ "$GIT_BRANCH" == "master" ]; then 
    npm run build:pro 
 fi
