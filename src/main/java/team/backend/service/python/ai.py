import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from sklearn.tree import DecisionTreeClassifier
from sklearn.tree import plot_tree
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'  # FATAL
# os.environ['TF_ENABLE_ONEDNN_OPTS'] = '0'
# import concurrent.futures

# 인공지능!!!! 어예~!
# 두개의 기업명을 받아서, db로부터 인출하여 normaling 및 라벨링된 dataFrame 산출-> calculate에서 수행

# dataFrame을 받아서 라벨링 훈련(3개월로 window로 옮겨가며 훈련)
# 인자를 받아서, normalNlabel을 호출하여 df를 생성하는 것으로 변경
def training(df: pd.DataFrame,window_size:int=60,model=DecisionTreeClassifier(max_depth=3),default='CLOSE')->list:

    # tf.get_logger().setLevel('ERROR')
#    X, y = prepare_training_data(df)
    result=[]

    # 훈련 데이터를 생성합니다.
    X, y = prepare_training_data(df, window_size)
    # 훈련 데이터와 테스트 데이터로 분할합니다.
    train_size = int(0.8 * len(X))
    X_train, y_train = X[:train_size], y[:train_size]
    X_test, y_test = X[train_size:], y[train_size:]
    
    # 3차원 배열로 변환합니다.
    train_arrays = [np.array(lst) for lst in X_train]
    X_train_3d = np.stack(train_arrays, axis=0)
    X_train_flatten = X_train_3d.reshape(X_train_3d.shape[0], -1)
    
#    print('X_train:',X_train)
    model.fit(X_train_flatten, y_train)

    # 테스트 데이터로 모델 평가
    test_arrays = [np.array(lst) for lst in X_test]
    X_test_3d=np.stack(test_arrays,axis=0)
    X_test_flatten=X_test_3d.reshape(X_test_3d.shape[0],-1)
    y_pred = model.predict(X_test_flatten)
    accuracy = accuracy_score(y_test, y_pred)
    # print("모델 정확도:", accuracy,flush=True)
    result.append("모델 정확도:"+str(accuracy))
    if(y_pred[-1]!=-1):

#        print("현재 주가전파관계가 존재합니다. 확인해보세요. 예측치:",y_pred[-1],"주 전파",flush=True)
        result.append(y_pred[-1])
        return result
    else:
#        print("아쉽게도 현재 주가 전파관계는 없는 것으로 보이네요.",flush=True)
        result.append(-1)
        return result

    # plt.figure(figsize=(20,15))
    # plot_tree(model, filled=True)
    # plt.show()
#    return model
    # plot_tree(model,filled=True,feature_names=[])
# dataFrame에서 훈련데이터 준비
def prepare_training_data(df, window_size=60)->tuple[list,list]:
    X = []  # 입력 특성
    y = []  # 타겟 값

    # 윈도우를 이동시켜가며 훈련 데이터 생성
    for i in range(len(df) - window_size):
        window = df.iloc[i:i+window_size]  # 윈도우 설정
        train_X=window.iloc[:-1,:2].to_numpy()
        
        #시간이 지남에 따라 망각 반영
        # for i in range(len(train_X)):
        #     train_X.iloc[i,0]*=(i+1)/59
        
        # 입력 특성: 윈도우의 처음부터 마지막 행의 이전까지
        X.append(train_X)

        # 타겟 값: 윈도우의 마지막 행의 'linkage' 열 값
        y.append(window.iloc[-1]['linkage'])

    return X, y

# 두개의 기업명과 특정 날짜를 입력받고, 예측
# model=DecisionTreeClassifier(max_depth=3)
# #model=training('004020','058430',model)
# training('1152','058430')


# import numpy as np
# import tensorflow as tf
# import gym

# # LSTM 모델 구축
# def build_lstm_model(window_size):
# #        print(window_size)
#     input_shape = ([window_size,3])
#     input_layer = tf.keras.layers.Input(shape=input_shape)
#     lstm_layer = tf.keras.layers.LSTM(16, return_sequences=True)(input_layer)
#     lstm_layer = tf.keras.layers.LSTM(16)(lstm_layer)
#     output_layer = tf.keras.layers.Dense(1, activation='sigmoid')(lstm_layer)
#     model = tf.keras.models.Model(inputs=input_layer, outputs=output_layer)  # 모델 정의
#     return model


# # 강화학습 환경 설정
# class PricePredictionEnv(gym.Env):
#     def __init__(self, df, window_size):
#         self.df = df
#         # self.price_data_2 = price_data_2
#         self.window_size = window_size
#         self.action_space = gym.spaces.Discrete(2)  # Buy or Hold
#         self.observation_space = gym.spaces.Box(low=0, high=1, shape=(window_size, 3), dtype=np.float32)
#         self.current_index = window_size
    
#     def reset(self):
#         self.current_index = self.window_size
#         return self._get_observation()
    
#     def _get_observation(self):
#         return self.df.iloc[self.current_index-self.window_size:self.current_index].values.reshape((self.window_size, 3))
    
#     def step(self, action):
#         done = False
#         reward = 0
#         self.current_index += 1
#         if self.current_index >= len(self.df):
#             done = True
#         observation = self._get_observation()
#         return observation, reward, done, {}
    
#     def render(self, mode='human'):
#         pass
#     def __getitem__(self, idx):
#         return self._get_observation()

# # Define a custom callback to print training progress
# # class ProgressLogger(tf.keras.callbacks.Callback):
# #     def on_epoch_end(self, epoch, logs=None):
# #         print(f"Epoch {epoch+1}/{self.params['epochs']}, Loss: {logs['loss']}, Accuracy: {logs['accuracy']}")

# # 시계열 df를 받아서 deep-learning
# def deep(df: pd.DataFrame):
#     tf.get_logger().setLevel('ERROR')
    
#     # 시계열 주가 데이터 생성 (임의의 데이터로 대체)
#     # 모델 학습
#     env = PricePredictionEnv(df, window_size=20)
#     model = build_lstm_model(env.window_size)
#     model.compile(optimizer=tf.keras.optimizers.Adam(), loss='binary_crossentropy', metrics=['accuracy'])
#     print("에피소드로 들어갑니다.")
#     for episode in range(10):
#         observation = env.reset()
#         done = False
#         max_iterations=30 #최대 반복횟수
#         current_iteration=0
#         while not done and current_iteration < max_iterations:
#             action = np.random.randint(0, 2)
#             next_observation, reward, done, _ = env.step(action)
#             model.train_on_batch(np.expand_dims(observation, axis=0), np.array([action]))
#             observation = next_observation
#             current_iteration+=1
#             print("batch훈련",current_iteration,"완료.")
#         print("에피소드",episode," 훈련 완료.")
    
    
    
#     # # Train the model with the custom callback
#     # X_train, y_train = env.get_training_data()  # 환경 객체에서 훈련 데이터와 라벨을 추출
#     # model.fit(X_train, y_train, epochs=100, callbacks=[ProgressLogger()])
    
#     # 모델 평가
#     total_rewards = []
    
#     for episode in range(10):
#         observation = env.reset()
#         done = False
#         total_reward = 0
#         max_iterations=30 #최대 반복횟수
#         current_iteration=0
#         while not done and current_iteration < max_iterations:
#             action_prob = model.predict(np.expand_dims(observation, axis=0))[0][0]
#             action = 1 if action_prob >= 0.5 else 0
#             next_observation, reward, done, _ = env.step(action)
#             total_reward += reward
#             observation = next_observation
#             current_iteration+=1
#         total_rewards.append(total_reward)

#     average_reward = np.mean(total_rewards)
#     print("Average reward:", average_reward)
    

